package controllers.support

import com.fasterxml.jackson.core.{JsonGenerator, JsonParser, Version}
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer, JsonSerializer, ObjectMapper, SerializerProvider}
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.apache.commons.lang3.StringUtils
import org.joda.time.{DateTime, LocalDate, LocalDateTime}
import play.api.http.ContentTypeOf
import play.api.http.ContentTypes
import play.api.http.Writeable
import play.api.mvc.{Results, BodyParsers, BodyParser, Codec}
import com.fasterxml.jackson.core.`type`.TypeReference
import java.lang.reflect.{Type, ParameterizedType}
import play.api.Play
import play.api.libs.iteratee.{Done, Iteratee, Traversable}
import play.api.libs.iteratee.Input.{El, Empty}

trait JsonSupport {
    def toJson(obj: AnyRef): JsonStr

    def readValue[T: Manifest](value: String): T

    def jsonParser[T: Manifest]: BodyParser[T] = BodyParsers.parse.when(
        _.contentType.exists(m => m == "text/json" || m == "application/json"),
        tolerantJson[T](BodyParsers.parse.DEFAULT_MAX_TEXT_LENGTH),
        request => Play.maybeApplication.map(_.global.onBadRequest(request, "Expecting text/json or application/json body")).getOrElse(Results.BadRequest)
    )

    private def tolerantJson[T: Manifest](maxLength: Int): BodyParser[T] = BodyParser("json, maxLength=" + maxLength) {
        request =>
            Traversable.takeUpTo[Array[Byte]](maxLength).apply(Iteratee.consume[Array[Byte]]().map {
                bytes =>
                    scala.util.control.Exception.allCatch[T].either {
                        this.readValue[T](new String(bytes, request.charset.getOrElse("utf-8")))
                    }.left.map {
                        e =>
                            (Play.maybeApplication.map(_.global.onBadRequest(request, "Invalid Json: \n" + e.getMessage + "\n" + e.getStackTraceString)).getOrElse(Results.BadRequest), bytes)
                    }
            }).flatMap(Iteratee.eofOrElse(Results.EntityTooLarge))
                    .flatMap {
                case Left(b) => Done(Left(b), Empty)
                case Right(it) => it.flatMap {
                    case Left((r, in)) => Done(Left(r), El(in))
                    case Right(json) => Done(Right(json), Empty)
                }
            }
    }

    class JsonStr(json: String) {
        override def toString = json
    }

    object JsonStr {
        implicit def writeableOf_jsonStr(implicit codec: Codec, ct: ContentTypeOf[JsonStr]): Writeable[JsonStr] = {
            Writeable(jsval => codec.encode(jsval.toString))
        }

        implicit def contentTypeOf_jsonStr(implicit codec: Codec): ContentTypeOf[JsonStr] = {
            ContentTypeOf[JsonStr](Some(ContentTypes.JSON))
        }
    }
}

