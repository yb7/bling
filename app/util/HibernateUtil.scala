package util

import org.slf4j.LoggerFactory
import org.hibernate.SessionFactory
import org.hibernate.cfg.{Configuration, AvailableSettings}
import com.wyb7.waffle.domain.entity.{ScalaBigDecimalUserType, UnderscoreNamingStrategy}
import org.hibernate.service.ServiceRegistryBuilder
import play.api.Play.current
import javax.sql.DataSource

/**
 * Author: Wang Yibin
 * Date: 13-1-10
 * Time: 下午9:26
 */
object HibernateUtil {
    private val logger = LoggerFactory.getLogger(getClass)

    def buildSessionFactory(): SessionFactory = {
        import AvailableSettings._

        val cfg = new Configuration()

        cfg.getProperties.put(DATASOURCE, play.api.db.DB.getDataSource())
        cfg.setProperty(DIALECT, "org.hibernate.dialect.MySQL5InnoDBDialect")
            .setProperty(HBM2DDL_AUTO, "update")
            .setProperty(USE_SECOND_LEVEL_CACHE, "false")
            .setProperty(CURRENT_SESSION_CONTEXT_CLASS, "thread")
            .setProperty(TRANSACTION_STRATEGY, "org.hibernate.transaction.JDBCTransactionFactory")
            .setNamingStrategy(new UnderscoreNamingStrategy)
            .registerTypeOverride(new ScalaBigDecimalUserType, Array(classOf[BigDecimal].getName))
        EntityClassScanner.scanPackages("models").foreach(cfg.addAnnotatedClass(_))

//        cfg.configure("hibernate_sp.cfg.xml")
        val  serviceRegistryBuilder = new ServiceRegistryBuilder().applySettings(cfg.getProperties)
        _sessionFactory = cfg.buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry())
        _sessionFactory
    }

    private var _sessionFactory: SessionFactory  = _

    def sessionFactory = _sessionFactory

    def currentSession = sessionFactory.getCurrentSession
}
