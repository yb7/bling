package controllers.support

import play.api.mvc.Results._
/**
 * Author: Wang Yibin
 * Date: 13-1-15
 * Time: 上午9:59
 */
object ResultsHelper {
    val SuccessJson = Ok("""{"success": true}""").as("application/json")
}
