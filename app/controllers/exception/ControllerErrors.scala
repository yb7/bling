package controllers.exception

import play.api.data.validation.ValidationError

import play.api.libs.json._
import play.api.i18n.Messages

/**
 * Author: Wang Yibin
 * Date: 13-3-28
 * Time: 下午4:12
 */
case class JsonInvalid(errors: Seq[(JsPath, Seq[ValidationError])]) extends Error(JsonInvalid.errorsToString(errors))

object JsonInvalid {
    def errorsToString(errors: Seq[(JsPath, Seq[ValidationError])]) = {
        errors.map( error => {
            val (jsPath, validationErrors) = (error._1, error._2)
            jsPath.toString() + " : " + validationErrors.map(e => Messages(e.message)).mkString("; ")
        }).mkString("\n")
    }
}
//object JsonInvalid {
//import play.api.http.ContentTypeOf
//import play.api.http.ContentTypes
//import play.api.http.Writeable
//import play.api.mvc.Codec
//    implicit def writeableOf_json_invalid(implicit codec: Codec): Writeable[JsonInvalid] = {
//        Writeable[JsonInvalid](v => Json.obj)
//    }
//
//    implicit def contentTypeOf_json_invalid(implicit codec: Codec): ContentTypeOf[JsonInvalid] = {
//        ContentTypeOf[JsonInvalid](Some(ContentTypes.JSON))
//    }
//}