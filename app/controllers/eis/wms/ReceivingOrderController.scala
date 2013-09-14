package controllers.eis.wms

import org.springframework.stereotype.Component
import play.api.mvc.{Controller, Action}
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import com.wyb7.waffle.commons.value.Pagination
import org.apache.commons.io.FileUtils
import eis.domain.model.wms.ReceivingOrder
import org.springframework.beans.factory.annotation.Autowired
import controllers.support.JacksonJsonSupport
import com.fasterxml.jackson.annotation.{JsonIgnore, JsonAutoDetect}
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility
import com.wyb7.waffle.commons.value.GenericActionResult._
import eis.application.wms.ReceivingOrderService
import org.slf4j.LoggerFactory
import org.joda.time.LocalDate
import com.wyb7.waffle.commons.util.SafeGetter._

/**
 * User: abin
 * Date: 13-9-12
 * Time: 下午2:31
 */
@Component
class ReceivingOrderController @Autowired()(receivingOrderService: ReceivingOrderService)
        extends Controller with JacksonJsonSupport {
    private val logger = LoggerFactory.getLogger(this.getClass)

    def importXls(id: Long) = Action(parse.multipartFormData) { implicit request =>
        val file = request.body.file("file").get
        val workbook = new HSSFWorkbook(FileUtils.openInputStream(file.ref.file))
        logger.debug(s"import articles from ${file.filename}")
        val errorMessages = receivingOrderService.importByXls(id, workbook)
        val result = if (errorMessages.hasError) {
            failureResult(errorMessages.messages.mkString("<br/>"))
        } else {
            successResult("成功")
        }
        OkJson(result)
    }
    def create() = Action { implicit request =>
        val order = receivingOrderService.createNew()
        OkJson(successResult(new ReceivingOrderHeadDto(order)))
    }
    def articles(id: Long) = Action {
        OkJson(successResult(receivingOrderService.articles(id).map(new ArticleDto(_))))
    }

    def findAll() = Action {
        OkJson(Pagination.createWithAllData(
            receivingOrderService.findAll.map(new ReceivingOrderHeadDto(_))
        ))
    }
    def deleteArticle(orderId: Long, articleId: Long) = Action {
        receivingOrderService.deleteArticle(articleId)
        OkJson(successResult(s"删除货品 ID[$articleId]"))
    }

    def execute(id: Long) = Action(jsonParser[ReceivingExecuteDto]) { implicit request =>
        val order = this.receivingOrderService.execute(id, request.body)
        OkJson(successResult(new ReceivingOrderHeadDto(order)))
    }
}

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class ReceivingOrderHeadDto(_order: ReceivingOrder) {
    @JsonIgnore
    val order = _order
    val id = order.id
    val version = order.version
    val bizCode = order.bizCode
    val receivingDate = order.receivingDate
    val warehouseId = nullSafe(order.warehouse.id)
    val warehouseName = nullSafe(order.warehouse.name)
}

case class ReceivingExecuteDto(receivingDate: LocalDate, warehouseId: Long)