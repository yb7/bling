package controllers.eis.wms

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import play.api.mvc.{Action, Controller}
import controllers.support.JacksonJsonSupport
import org.slf4j.LoggerFactory
import eis.application.wms.DistributionAllocationService
import com.fasterxml.jackson.annotation.{JsonIgnore, JsonAutoDetect}
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility
import com.wyb7.waffle.commons.util.SafeGetter._
import eis.domain.model.wms.DistributionAllocation
import com.wyb7.waffle.commons.value.GenericActionResult._
import org.joda.time.LocalDate

/**
 * User: abin
 * Date: 13-9-15
 * Time: 上午8:14
 */
@Component
class DistributionAllocationController @Autowired()(distributionAllocationService: DistributionAllocationService)
        extends Controller with JacksonJsonSupport {
    private val logger = LoggerFactory.getLogger(this.getClass)

    def create() = Action {
        val order = distributionAllocationService.createNew()
        OkJson(successResult(new DistributionAllocationHeadDto(order)))
    }
    def articles(id: Long) = Action {
        OkJson(successResult(distributionAllocationService.articles(id).map(new ArticleDto(_))))
    }
    def findAll() = Action {
        OkJson(successResult(distributionAllocationService.findAll().map(new DistributionAllocationHeadDto(_))))
    }
    def execute(id: Long) = Action(jsonParser[DistributionAllocationExecuteDto]) { implicit request =>
        distributionAllocationService.execute(id, request.body)
        Ok
    }
    def addArticles(orderId: Long) = Action(jsonParser[Set[Long]]) { implicit request =>
        distributionAllocationService.addArticles(orderId, request.body)
        Ok
    }
    def deleteArticle(orderId: Long, articleIds: String) = Action {
        distributionAllocationService.deleteArticle(orderId, articleIds.split(",").toSet.map{x: String => x.toLong})
        OkJson(successResult(s"删除货品 ID[${articleIds.mkString(",")}]"))
    }
}


@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class DistributionAllocationHeadDto(_order: DistributionAllocation) {
    @JsonIgnore
    val order = _order
    val id = order.id
    val version = order.version
    val bizCode = order.bizCode
    val distributionDate = order.distributionDate
//    val sendWarehouseId = nullSafe(order.sendWarehouse.id)
//    val sendWarehouseName = nullSafe(order.sendWarehouse.name)

    val receiveWarehouseId = nullSafe(order.receiveWarehouse.id)
    val receiveWarehouseName = nullSafe(order.receiveWarehouse.name)
    val remark = order.remark
}

case class DistributionAllocationExecuteDto(distributionDate: LocalDate, receiveWarehouseId: Long, remark: String)