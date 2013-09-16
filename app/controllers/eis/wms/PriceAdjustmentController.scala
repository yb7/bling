package controllers.eis.wms

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import play.api.mvc.{Action, Controller}
import controllers.support.JacksonJsonSupport
import org.slf4j.LoggerFactory
import eis.application.wms.PriceAdjustmentService
import com.fasterxml.jackson.annotation.{JsonIgnore, JsonAutoDetect}
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility
import com.wyb7.waffle.commons.util.SafeGetter._
import eis.domain.model.wms.PriceAdjustment
import com.wyb7.waffle.commons.value.GenericActionResult._
import org.joda.time.LocalDate

/**
 * User: abin
 * Date: 13-9-15
 * Time: 上午8:14
 */
@Component
class PriceAdjustmentController @Autowired()(priceAdjustmentService: PriceAdjustmentService)
        extends Controller with JacksonJsonSupport {
    private val logger = LoggerFactory.getLogger(this.getClass)

    def create() = Action {
        val order = priceAdjustmentService.createNew()
        OkJson(successResult(new PriceAdjustmentHeadDto(order)))
    }
    def articles(id: Long) = Action {
        OkJson(successResult(priceAdjustmentService.articles(id).map(new ArticleDto(_))))
    }
    def findAll() = Action {
        OkJson(successResult(priceAdjustmentService.findAll().map(new PriceAdjustmentHeadDto(_))))
    }
    def execute(id: Long) = Action(jsonParser[PriceAdjustmentExecuteDto]) { implicit request =>
        priceAdjustmentService.execute(id, request.body)
        Ok
    }
    def addArticles(orderId: Long) = Action(jsonParser[Set[Long]]) { implicit request =>
        priceAdjustmentService.addArticles(orderId, request.body)
        Ok
    }
    def deleteArticle(orderId: Long, articleIds: String) = Action {
        priceAdjustmentService.deleteArticle(orderId, articleIds.split(",").toSet.map{x: String => x.toLong})
        OkJson(successResult(s"删除货品 ID[${articleIds.mkString(",")}]"))
    }
}


@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class PriceAdjustmentHeadDto(_order: PriceAdjustment) {
    @JsonIgnore
    val order = _order
    val id = order.id
    val version = order.version
    val bizCode = order.bizCode
    val remark = order.remark
    val effectiveDate = order.effectiveDate
}

case class PriceAdjustmentExecuteDto(effectiveDate: LocalDate, receiveWarehouseId: Long, remark: String)