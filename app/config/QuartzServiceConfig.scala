package config

import org.springframework.context.annotation.{Bean, Configuration}
import java.util.Properties
import org.quartz.impl.StdSchedulerFactory
import org.springframework.scheduling.quartz.{SpringBeanJobFactory, SchedulerFactoryBean}
import org.quartz.JobBuilder._
import org.quartz.TriggerBuilder._
import org.quartz.SimpleScheduleBuilder._
import javax.sql.DataSource
import org.springframework.beans.factory.annotation.Autowired
import org.joda.time.{LocalTime, LocalDate}
import eis.domain.service.wms.PriceAdjustmentJob
import javax.transaction.{TransactionManager, Transaction}
import org.springframework.transaction.PlatformTransactionManager


/**
 * Author: Wang Yibin
 * Date: 12-6-8
 * Time: 下午1:21
 */
@Configuration
class QuartzServiceConfig {
    @Autowired
    var dataSource: DataSource = _
    @Autowired
    var transactionManager: PlatformTransactionManager = _

    @Bean(destroyMethod = "destroy")
    def quartzSchedulerFactory = {
        val sf = new SchedulerFactoryBean
        val properties = new Properties
        properties.setProperty(StdSchedulerFactory.PROP_TABLE_PREFIX, "QUTZ_")

        sf.setQuartzProperties(properties)
        sf.setJobFactory(new SpringBeanJobFactory)
        sf.setDataSource(dataSource)
        sf.setTransactionManager(transactionManager)
        sf.setJobDetails(Array(priceAdjustmentJob))
        sf.setApplicationContextSchedulerContextKey("applicationContext")
        sf.setStartupDelay(60)
        // Always want to get the scheduler, even if it isn't starting,
        // to make sure it is both initialized and registered.
//        val scheduler = sf.getScheduler()
        sf
    }


    private val priceAdjustmentJob = newJob(classOf[PriceAdjustmentJob])
            .withIdentity("PriceAdjustment", "Sys")
            .withDescription("调价")
            .storeDurably()
            .build()


}
