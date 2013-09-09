
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.ApplicationContext
import play.api._
import util.AppServiceConfig

/**
 * Author: Wang Yibin
 * Date: 13-1-10
 * Time: 下午9:23
 */
object Global extends GlobalSettings {


    private var ctx: ApplicationContext = _

    override def onStart(app: Application) {
        super.onStart(app)
        Logger.info("Application has started")
//        ctx = new ClassPathXmlApplicationContext("applicationContext.xml")
        ctx = new AnnotationConfigApplicationContext(classOf[AppServiceConfig])
    }

    override def getControllerInstance[A](controllerClass: Class[A]) = {
        ctx.getBean(controllerClass)
    }
}
