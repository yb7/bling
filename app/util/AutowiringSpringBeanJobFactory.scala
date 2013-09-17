package util

import org.springframework.scheduling.quartz.SpringBeanJobFactory
import org.springframework.context.{ApplicationContext, ApplicationContextAware}
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.quartz.spi.TriggerFiredBundle

/**
 * User: abin
 * Date: 13-9-17
 * Time: 下午3:12
 */
class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory
    with ApplicationContextAware {

    @transient
    private var beanFactory: AutowireCapableBeanFactory = _

    def setApplicationContext(context: ApplicationContext) {
        beanFactory = context.getAutowireCapableBeanFactory()
    }

    override protected def createJobInstance(bundle: TriggerFiredBundle): AnyRef = {
        val job = super.createJobInstance(bundle)
        beanFactory.autowireBean(job)
        job
    }

}
