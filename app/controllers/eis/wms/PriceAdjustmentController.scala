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
    val retailPriceDirectly = order.retailPriceDirectly

    val incrementBaseOnCost = order.incrementBaseOnCost
    val coefficientBaseOnCost = order.coefficientBaseOnCost

    val incrementBaseOnRetailPrice = order.incrementBaseOnRetailPrice
    val coefficientBaseOnRetailPrice = order.coefficientBaseOnRetailPrice

    val excludeUnits0 = order.excludeUnits0
    val excludeUnits1 = order.excludeUnits1
    val excludeUnits2 = order.excludeUnits2
    val excludeUnits3 = order.excludeUnits3
    val excludeUnits4 = order.excludeUnits4
    val excludeUnits5 = order.excludeUnits5
    val excludeUnits6 = order.excludeUnits6
    val excludeUnits7 = order.excludeUnits7
    val excludeUnits8 = order.excludeUnits8
    val excludeUnits9 = order.excludeUnits9
    val excludeUnits = order.excludeUnits.mkString(",")
}

case class PriceAdjustmentExecuteDto(
        effectiveDate: LocalDate, remark: String,
        retailPriceDirectly: BigDecimal,
        incrementBaseOnCost: BigDecimal, coefficientBaseOnCost: BigDecimal,
        incrementBaseOnRetailPrice: BigDecimal, coefficientBaseOnRetailPrice: BigDecimal,
        excludeUnits0: Boolean, excludeUnits1: Boolean,excludeUnits2: Boolean,
        excludeUnits3: Boolean, excludeUnits4: Boolean,excludeUnits5: Boolean,
        excludeUnits6: Boolean, excludeUnits7: Boolean,excludeUnits8: Boolean,
        excludeUnits9: Boolean
)