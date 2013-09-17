package config

import org.springframework.context.annotation.{Import, FilterType, ComponentScan, Configuration, Bean}
import org.springframework.context.annotation.ComponentScan.Filter
import org.hibernate.SessionFactory
import org.springframework.orm.hibernate4.{LocalSessionFactoryBuilder, HibernateTransactionManager}
import play.api.Play.current
import org.hibernate.cfg.AvailableSettings._
import com.wyb7.waffle.domain.entity.{ScalaBigDecimalUserType, UnderscoreNamingStrategy}
import org.springframework.transaction.annotation.{TransactionManagementConfigurer, EnableTransactionManagement}
import org.slf4j.LoggerFactory
import org.springframework.transaction.PlatformTransactionManager

/**
 * User: abin
 * Date: 13-9-7
 * Time: 上午9:45
 */ 
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(
    basePackages = Array("controllers", "eis"),
    excludeFilters = Array(new Filter(`type` = FilterType.ANNOTATION, value = Array(classOf[Configuration])))
)
@Import(Array(classOf[QuartzServiceConfig]))
class AppServiceConfig extends TransactionManagementConfigurer {
    val logger = LoggerFactory.getLogger(classOf[AppServiceConfig])

    @Bean
    def sessionFactory: SessionFactory = {
        logger.info("start to build session factory")
        val builder = new LocalSessionFactoryBuilder(dataSource)

        builder.setProperty(DIALECT, "org.hibernate.dialect.MySQL5InnoDBDialect")
            .setProperty(HBM2DDL_AUTO, "update")
            .setProperty(USE_SECOND_LEVEL_CACHE, "false")
            .setNamingStrategy(new UnderscoreNamingStrategy)
            .registerTypeOverride(new ScalaBigDecimalUserType, Array(classOf[BigDecimal].getName))

        builder.scanPackages("eis.domain.model").buildSessionFactory()
    }
    @Bean(name = Array("transactionManager"))
    def transactionManager: PlatformTransactionManager = {
        new HibernateTransactionManager(sessionFactory)
    }

    @Bean
    def dataSource = play.api.db.DB.getDataSource()

    def annotationDrivenTransactionManager(): PlatformTransactionManager = transactionManager
}
