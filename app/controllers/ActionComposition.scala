package controllers

/**
 * Author: Wang Yibin
 * Date: 13-1-10
 * Time: 下午10:41
 */

import _root_.util.HibernateUtil
import play.api.mvc._
import play.api.mvc.Results._

trait Secured {
    case class AuthenticatedRequest[A](user: SecuredUser, request: Request[A]) extends WrappedRequest(request)

    def Authenticated[A](parser: BodyParser[A], readOnly: Boolean = false)(f: AuthenticatedRequest[A] => Result) = {
        ExceptionAjaxWrapper {
            InTransaction {
                Action(parser) { implicit request =>
                    f(AuthenticatedRequest(null, request))
                }
            }
        }
    }
    case class SecuredUser(id: Long, fullname: String)

    // Overloaded method to use the default body parser
    import play.api.mvc.BodyParsers._
    def Authenticated(f: AuthenticatedRequest[AnyContent] => Result): Action[AnyContent]  = {
        Authenticated(parse.anyContent)(f)
    }

    def InTransaction[A](action: Action[A]): Action[A] = {
        Action(action.parser) { request =>
            try {
                HibernateUtil.currentSession.beginTransaction()
                val result = action(request)
                HibernateUtil.currentSession.getTransaction.commit()
                result
            } catch {
                case ex: Throwable => HibernateUtil.currentSession.getTransaction.rollback(); throw ex
            }
        }
    }

    def ExceptionAjaxWrapper[A](action: Action[A]): Action[A] = {
        Action(action.parser) { request =>
            if (request.contentType.isDefined && request.contentType.get == "application/json") {
                try {
                    action(request)
                } catch {
                    case ex: Throwable => Ok(s"""{"success": false, "message": "${ex.getMessage}"}""").as("application/json")
                }
            } else {
                action(request)
            }
        }
    }
}

trait SecuredController extends Controller  with Secured
