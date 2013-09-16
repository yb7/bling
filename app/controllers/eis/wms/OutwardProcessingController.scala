package controllers.eis.wms

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import play.api.mvc.{Action, Controller}
import controllers.support.JacksonJsonSupport
import org.slf4j.LoggerFactory
import eis.application.wms.OutwardProcessingService
import com.fasterxml.jackson.annotation.{JsonIgnore, JsonAutoDetect}
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility
import com.wyb7.waffle.commons.util.SafeGetter._
import eis.domain.model.wms.OutwardProcessing
import com.wyb7.waffle.commons.value.GenericActionResult._
import org.joda.time.LocalDate

/**
 * User: abin
 * Date: 13-9-15
 * Time: 上午8:14
 */
@Component
class OutwardProcessingController @Autowired()(outwardProcessingService: OutwardProcessingService)
        extends Controller with JacksonJsonSupport {
    private val logger = LoggerFactory.getLogger(this.getClass)

    def create() = Action {
        val order = outwardProcessingService.createNew()
        OkJson(successResult(new OutwardProcessingHeadDto(order)))
    }
    def articles(id: Long) = Action {
        OkJson(successResult(outwardProcessingService.articles(id).map(new ArticleDto(_))))
    }
    def findAll() = Action {
        OkJson(successResult(outwardProcessingService.findAll().map(new OutwardProcessingHeadDto(_))))
    }
    def execute(id: Long) = Action(jsonParser[OutwardProcessingExecuteDto]) { implicit request =>
        outwardProcessingService.execute(id, request.body)
        Ok
    }
    def addArticles(orderId: Long) = Action(jsonParser[Set[Long]]) { implicit request =>
        outwardProcessingService.addArticles(orderId, request.body)
        Ok
    }
    def deleteArticle(orderId: Long, articleIds: String) = Action {
        outwardProcessingService.deleteArticle(orderId, articleIds.split(",").toSet.map{x: String => x.toLong})
        OkJson(successResult(s"删除货品 ID[${articleIds.mkString(",")}]"))
    }
}


@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class OutwardProcessingHeadDto(_order: OutwardProcessing) {
    @JsonIgnore
    val order = _order
    val id = order.id
    val version = order.version
    val bizCode = order.bizCode
    val outwardDate = order.outwardDate
    val expectedCompletionDate = order.expectedCompletionDate
    val redOrBlue = order.redOrBlue
//    val sendWarehouseId = nullSafe(order.sendWarehouse.id)
//    val sendWarehouseName = nullSafe(order.sendWarehouse.name)

    val receiveWarehouseId = nullSafe(order.receiveWarehouse.id)
    val receiveWarehouseName = nullSafe(order.receiveWarehouse.name)
    val remark = order.remark
}

case class OutwardProcessingExecuteDto(outwardDate: LocalDate, expectedCompletionDate: LocalDate,
               redOrBlue: String, receiveWarehouseId: Long, remark: String)