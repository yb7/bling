package controllers.util

import play.api.libs.json._
import play.api.data.validation.ValidationError
import controllers.exception.JsonInvalid
import org.apache.commons.lang3.StringUtils
import eis.domain.shared.{Given, NotGiven, GivenOrNot}

/**
 * Author: Wang Yibin
 * Date: 13-4-10
 * Time: 上午10:10
 */
trait ExtendReads {
    /**
     * Deserializer for Int types.
     */
    implicit object IntReads extends Reads[Int] {
        def reads(json: JsValue) = json match {
            case JsNumber(n) => JsSuccess(n.toInt)
            case JsString(s) if StringUtils.isNumeric(s) => JsSuccess(s.toInt)
            case _ => JsError(Seq(JsPath() -> Seq(ValidationError("validate.error.expected.jsnumber"))))
        }
    }

    /**
     * Deserializer for Short types.
     */
    implicit object ShortReads extends Reads[Short] {
        def reads(json: JsValue) = json match {
            case JsNumber(n) => JsSuccess(n.toShort)
            case JsString(s) if StringUtils.isNumeric(s) => JsSuccess(s.toShort)
            case _ => JsError(Seq(JsPath() -> Seq(ValidationError("validate.error.expected.jsnumber"))))
        }
    }

    /**
     * Deserializer for Long types.
     */
    implicit object LongReads extends Reads[Long] {
        def reads(json: JsValue) = json match {
            case JsNumber(n) => JsSuccess(n.toLong)
            case JsString(s) if StringUtils.isNumeric(s) => JsSuccess(s.toLong)
            case _ => JsError(Seq(JsPath() -> Seq(ValidationError("validate.error.expected.jsnumber"))))
        }
    }

    /**
     * Deserializer for Float types.
     */
    implicit object FloatReads extends Reads[Float] {
        def reads(json: JsValue) = json match {
            case JsNumber(n) => JsSuccess(n.toFloat)
            case JsString(s) if StringUtils.isNumeric(s) => JsSuccess(s.toFloat)
            case _ => JsError(Seq(JsPath() -> Seq(ValidationError("validate.error.expected.jsnumber"))))
        }
    }

    /**
     * Deserializer for Double types.
     */
    implicit object DoubleReads extends Reads[Double] {
        def reads(json: JsValue) = json match {
            case JsNumber(n) => JsSuccess(n.toDouble)
            case JsString(s) if StringUtils.isNumeric(s) => JsSuccess(s.toDouble)
            case _ => JsError(Seq(JsPath() -> Seq(ValidationError("validate.error.expected.jsnumber"))))
        }
    }

    implicit class RichJsPath(path: JsPath) {
        def readGivenOrNot[T](implicit r: Reads[T]) = Reads[GivenOrNot[T]]{ json =>
            path.applyTillLast(json).fold(
                jserr => jserr,
                jsres => jsres.fold(
                    _ => JsSuccess(NotGiven),
                    jsValue => r.reads(jsValue).repath(path).map(Given(_))
                )
            )
        }
        def readIgnore[T](implicit r: Reads[T]) = Reads[Null] { json =>
            JsSuccess(null)
        }
    }
    def notBlank(implicit reads:Reads[String]): Reads[String] =
        reads.filter(ValidationError("validate.error.expected.notblank"))(StringUtils.isNotBlank(_))

    implicit class JsResultWrapper[+A](jsResult: JsResult[A]) {
        def validOrThrow[X](valid: A => X): X = {
            jsResult.fold(
                invalid = {errors => throw JsonInvalid(errors)},
                valid = valid
            )
        }
    }
}

trait ExtendWrites {
//    implicit object ObjectIdWrites extends Writes[ObjectId] {
//        def writes(o: ObjectId) = JsString(o.toString)
//    }

}

trait WriteableDef extends JsonUtil {
//    import play.api.mvc.Codec
//    import play.api.http.{Writeable, ContentTypes, ContentTypeOf}
//
//    implicit def contentTypeOf_versioned(implicit codec: Codec): ContentTypeOf[Pagination] = {
//        ContentTypeOf[Pagination](Some(ContentTypes.JSON))
//    }
//    implicit def writeableOf_versioned(implicit codec: Codec): Writeable[Pagination] = {
//        Writeable { p => codec.encode(
//            Json.obj(
//                "sEcho" -> p.sEcho, "iTotalRecords" -> p.iTotalRecords,
//                "iTotalDisplayRecords" -> p.iTotalDisplayRecords,
//                "aaData" ->  p.aaData.mapToJsonArray(_.mapToJsonArray())
//            ).toString()
//        )}
//    }
}

trait JsonUtil {
    implicit class IterableJsonSupport[T](list: Iterable[T]) {
        def mapToJsonArray(f : T => JsValue = basicValueToJsValue) = {
            list.foldLeft(JsArray(List()))((arr, obj) => arr ++ Json.arr(f(obj)))
        }
        private def basicValueToJsValue(v: T) = {
            if (v == null) {
                JsNull
            } else if (v.isInstanceOf[Int]) {
                JsNumber(v.asInstanceOf[Int])
            } else if (v.isInstanceOf[Long]) {
                JsNumber(v.asInstanceOf[Long])
            } else if (v.isInstanceOf[Double]) {
                JsNumber(v.asInstanceOf[Double])
            } else if (v.isInstanceOf[BigDecimal]) {
                JsNumber(v.asInstanceOf[BigDecimal])
            } else if (v.isInstanceOf[String]) {
                JsString(v.asInstanceOf[String])
            } else {
                JsUndefined(s"unknow value $v")
            }
        }
    }
}

object ExtendJson extends ExtendReads with ExtendWrites with WriteableDef
