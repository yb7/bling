package util

import org.springframework.context.annotation.{Import, PropertySource, ImportResource, FilterType, ComponentScan, Configuration, Bean}
import org.springframework.context.annotation.ComponentScan.Filter
import org.hibernate.SessionFactory
import org.springframework.orm.hibernate4.HibernateTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import java.security.{AccessController, Security}
import org.springframework.mail.javamail.{JavaMailSender, JavaMailSenderImpl}
import play.api.Play.current

/**
 * User: abin
 * Date: 13-9-7
 * Time: 上午9:45
 */
@Configuration
@ComponentScan(
    basePackages = Array("controllers", "models", "service"),
    excludeFilters = Array(new Filter(`type` = FilterType.ANNOTATION,
        value = Array(classOf[Configuration])))
)
class AppServiceConfig {
    @Bean(name = Array("sessionFactory"))
    def sessionFactory: SessionFactory = {
        HibernateUtil.buildSessionFactory()
    }
    @Bean
    def transactionManager: HibernateTransactionManager = {
        val tx = new HibernateTransactionManager
        tx.setSessionFactory(sessionFactory)
        tx
    }

    @Bean
    def transactionTemplate: TransactionTemplate = {
        val t = new TransactionTemplate
        t.setTransactionManager(transactionManager)
        t
    }


//    @Bean
//    def mailSender: JavaMailSender = {
//        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
//
//        val sender = new JavaMailSenderImpl
//        sender.setHost(current.configuration.getString("email.host"))//. env.getProperty("email.host"))
//        sender.setPort(env.getProperty("email.port").toInt)
//        sender.setUsername(env.getProperty("email.username"))
//        sender.setPassword(env.getProperty("email.password"))
//
//        val props = new Properties()
//
//
//        props.setProperty("mail.smtp.auth", "true")
//        props.setProperty("mail.smtp.starttls.enable", "true")
//        props.put("mail.smtp.socketFactory.port", env.getProperty("email.port"))
//        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
//        props.put("mail.smtp.socketFactory.fallback", "false")
//
//        sender.setJavaMailProperties(props)
//        sender
//    }
}
