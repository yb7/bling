package util

import org.slf4j.LoggerFactory
import logistics.core.util.TransactionHelper._
import org.quartz.{Job, JobExecutionException, JobExecutionContext}
import reflect.BeanProperty
import org.springframework.context.ApplicationContext

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

    def executeInTransaction(context: JobExecutionContext)
}

abstract class JobInSpringContext extends Job {
    @BeanProperty var applicationContext: ApplicationContext = _

    def getBean[T](clazz: Class[T]) = applicationContext.getBean(clazz)
}
