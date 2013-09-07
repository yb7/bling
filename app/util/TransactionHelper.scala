package util

import org.slf4j.LoggerFactory
import org.hibernate.Session

/**
 * Author: Wang Yibin
 * Date: 12-6-8
 * Time: 上午11:45
 */

object TransactionHelper {
    val logger = LoggerFactory.getLogger("TransactionHelper")

    def requiresNewTransaction[T](f: Session => T) = {
        val session = HibernateUtil.sessionFactory.openSession()
        val tx = session.beginTransaction()
        try {
            val result = f(session)
            tx.commit()
            result
        } catch {
            case ex: Throwable => tx.rollback(); throw ex
        } finally {
            session.close()
        }
//        getTransactionTemplate(propagationBehavior = TransactionDefinition.PROPAGATION_REQUIRES_NEW).execute(new TransactionCallback[T]() {
//            def doInTransaction(txStatus: TransactionStatus):T = {
//                f
//            }
//        })
    }

    def requireTransaction[T](f: => T) = {
        val session = HibernateUtil.sessionFactory.getCurrentSession
        try {
            session.beginTransaction()
            val result = f
            session.getTransaction.commit()
            result
        } catch {
            case ex: Throwable => session.getTransaction.rollback(); throw ex
        }
    }
//
//    def getTransactionTemplate(propagationBehavior: Int = TransactionDefinition.PROPAGATION_REQUIRED, timeout: Int = 30) = {
//        val transactionManager = implicitly[AppContext].getBean(classOf[PlatformTransactionManager])
//        val transactionTemplate = new TransactionTemplate(transactionManager)
//        transactionTemplate.setPropagationBehavior(propagationBehavior)
//        transactionTemplate.setTimeout(timeout)
//        transactionTemplate
//    }
}
