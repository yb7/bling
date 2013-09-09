package controllers.support

import play.api.mvc.Results._

/**
 * User: abin
 * Date: 13-9-9
 * Time: 下午3:45
 */
trait ExtendJsonContent { this: JsonSupport =>

    val OkJson = (obj: AnyRef) => Ok(toJson(obj))
}
