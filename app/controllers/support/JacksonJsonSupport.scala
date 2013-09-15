package controllers.support

import com.fasterxml.jackson.core.`type`.TypeReference
import java.lang.reflect.{Type, ParameterizedType}
import org.joda.time.{DateTime, LocalDateTime, LocalDate}
import org.apache.commons.lang3.StringUtils
import com.fasterxml.jackson.databind._
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.core.{JsonGenerator, JsonParser, Version}
import deser.ValueInstantiator

/**
 * Author: Wang Yibin
 * Date: 13-1-11
 * Time: 下午1:00
 */
trait JacksonJsonSupport extends JsonSupport with ExtendJsonContent {
    def toJson(obj: AnyRef): JsonStr = {
        new JsonStr(JacksonJsonSupport.objectMapper.writeValueAsString(obj))
    }

    def readValue[T: Manifest](json: String): T = {
        JacksonJsonSupport.objectMapper.readValue(json, new TypeReference[T]() {
            override def getType = new ParameterizedType {
                val getActualTypeArguments = manifest[T].typeArguments.map(_.runtimeClass.asInstanceOf[Type]).toArray
                val getRawType = manifest[T].runtimeClass
                val getOwnerType = null
            }
        })
    }
}

object JacksonJsonSupport {

    val objectMapper: ObjectMapper = {
        val objectMapper = new ObjectMapper()
        objectMapper.registerModule(new DefaultScalaModule)

        implicit def stringValueInstantiator[T <: AnyRef : Manifest](f: String => T) = new ValueInstantiator {
            def getValueTypeDesc = manifest[T].runtimeClass.getName
            override def canCreateFromString = true
            override def createFromString(ctxt: DeserializationContext, value: String) = f(value)
        }

        val simpleModule = new SimpleModule("SimpleModule", new Version(1, 0, 0, null, "wyb", "wyb"))
                .addDeserializer(classOf[String], StringTrimToNull)
//                .addDeserializer(classOf[LocalDate], StringToJodaLocalDate)
                .addDeserializer(classOf[LocalDateTime], StringToLocalDateTime)
                .addDeserializer(classOf[DateTime], StringToDateTime)
                .addDeserializer(classOf[java.util.Date], StringToUtilDate)
                .addSerializer(classOf[LocalDate], JustToString[LocalDate])
                .addSerializer(classOf[LocalDateTime], JustToString[LocalDateTime])
                .addSerializer(classOf[DateTime], JustToString[DateTime])
                //                    .addSerializer(classOf[java.util.Date], JustToString[java.util.Date])
                .addSerializer(classOf[Enumeration#Value], JustToString[Enumeration#Value])
                .addValueInstantiator(classOf[BigDecimal], {x: String =>BigDecimal(x)})
                .addValueInstantiator(classOf[LocalDate], {x: String =>LocalDate.parse(x)})
        objectMapper.registerModule(simpleModule)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper
    }



    private val StringTrimToNull = strTo {
        str => str
    }
//    private val StringToJodaLocalDate = strTo {
//        LocalDate.parse(_)
//    }
    private val StringToLocalDateTime = strTo {
        LocalDateTime.parse(_)
    }
    private val StringToUtilDate = strTo { str =>
        if (StringUtils.isNumeric(str)) {
            new java.util.Date(str.toLong)
        } else {
            DateTime.parse(str).toDate
        }
    }
    //http://zh.wikipedia.org/wiki/ISO_8601
    private val StringToDateTime = strTo {
        DateTime.parse(_)
    }

    private def JustToString[T] = toStr[T] {
        t => t.toString
    }

    private def strTo[T](f: String => T) = new JsonDeserializer[T] {
        def deserialize(jp: JsonParser, ctxt: DeserializationContext): T = {
            if (StringUtils.isBlank(jp.getText)) {
                null.asInstanceOf[T]
            } else {
                f(StringUtils.trimToNull(jp.getText))
            }
        }
    }

    private def toStr[T](f: T => String) = new JsonSerializer[T] {
        def serialize(value: T, gen: JsonGenerator, provider: SerializerProvider) {
            gen.writeString(f(value))
        }
    }
}