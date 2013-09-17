package util

import org.slf4j.LoggerFactory
import org.quartz.{Job, JobExecutionException, JobExecutionContext}
import beans.BeanProperty
import org.springframework.context.ApplicationContext
import org.springframework.transaction.{TransactionStatus, PlatformTransactionManager, TransactionDefinition}
import org.springframework.transaction.support.{TransactionCallbackWithoutResult, TransactionCallback, TransactionTemplate}

/**
 * User: houhou
 * Date: 12-9-3
 * Time: 下午9:51
 */

abstract class JobInTransaction extends JobInSpringContext {
    private val logger = LoggerFactory.getLogger(classOf[JobInTransaction])

    def execute(context: JobExecutionContext) {
        try {
            requireTransaction {
                executeInTransaction(context)
            }
        } catch {
            case e:Exception => {
                logger.error("Error In Job", e)
                throw new JobExecutionException(e)
            }
        }
    }

    private def requireTransaction[T](f: => T) = {
        getTransactionTemplate().execute(new TransactionCallbackWithoutResult() {
            def doInTransactionWithoutResult(txStatus: TransactionStatus) {
                f
            }
        })
    }

    private def getTransactionTemplate(propagationBehavior: Int = TransactionDefinition.PROPAGATION_REQUIRED, timeout: Int = 30) = {
        val transactionManager = getBean(classOf[PlatformTransactionManager])
        val transactionTemplate = new TransactionTemplate(transactionManager)
        transactionTemplate.setPropagationBehavior(propagationBehavior)
        transactionTemplate.setTimeout(timeout)
        transactionTemplate
    }

    def executeInTransaction(context: JobExecutionContext)
}

abstract class JobInSpringContext extends Job {
    @BeanProperty var applicationContext: ApplicationContext = _

    def getBean[T](clazz: Class[T]) = applicationContext.getBean(clazz)
}
