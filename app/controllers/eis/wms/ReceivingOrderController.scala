package controllers.eis.wms

import org.springframework.stereotype.Component
import play.api.mvc.{Controller, Action}
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.commons.lang3.StringUtils
import com.wyb7.waffle.commons.value.{Pagination, GenericActionResult}
import org.apache.commons.io.FileUtils
import eis.domain.model.wms.ReceivingOrder
import org.springframework.beans.factory.annotation.Autowired
import controllers.support.JacksonJsonSupport
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility
import com.wyb7.waffle.commons.value.GenericActionResult._
import eis.application.wms.ReceivingOrderService
import org.slf4j.LoggerFactory

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
        val order = receivingOrderService.findById(id).get
        val errorMessages = receivingOrderService.importByXls(order, workbook)
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
        OkJson(successResult(receivingOrderService.findById(id).get.listArticles.map(new ArticleDto(_))))
    }

    def findAll() = Action {
        OkJson(Pagination.createWithAllData(
            receivingOrderService.findAll.map(new ReceivingOrderHeadDto(_))
        ))
    }
}

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class ReceivingOrderHeadDto(order: ReceivingOrder) {
    val id = order.id
    val version = order.version
    val bizCode = order.bizCode
    val receivingTime = order.receivingTime
}
