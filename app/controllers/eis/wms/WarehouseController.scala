package controllers.eis.wms

import org.springframework.stereotype.Component
import play.api.mvc._
import controllers.support.JacksonJsonSupport
import com.wyb7.waffle.commons.value.Pagination
import org.springframework.beans.factory.annotation.Autowired
import eis.application.wms.WarehouseService
import eis.domain.model.wms.Warehouse
import com.wyb7.waffle.commons.value.GenericActionResult._
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility

/**
 * User: abin
 * Date: 13-9-7
 * Time: 下午1:32
 */
@Component
class WarehouseController @Autowired()(warehouseService: WarehouseService) extends Controller with JacksonJsonSupport {
    def query() = Action { implicit request =>
        OkJson(Pagination.createWithAllData(warehouseService.query().map(new WarehouseDto(_))))
    }

    def create() = Action(jsonParser[WarehouseCreateDto]) { implicit request =>
        val dto = request.body
        val domain = new Warehouse
        domain.name = dto.name
        domain.shortCode = dto.shortCode
        warehouseService.createNew(domain)
        OkJson(successResult(new WarehouseDto(domain)))
    }

    def update(id: Long) = Action(jsonParser[WarehouseCreateDto]) { implicit request =>
        val dto = request.body
        warehouseService.findById(id) match {
            case Some(warehouse) => warehouse.name = dto.name
                warehouse.shortCode = dto.shortCode
                warehouseService.update(warehouse)
                OkJson(successResult(new WarehouseDto(warehouse)))
            case None => NotFound
        }
    }

    def delete(id: Long) = Action { implicit request =>
        warehouseService.delete(id)
        OkJson(successResult(s"删除仓库 ID[$id]"))

    }
}

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class WarehouseDto(w: Warehouse) {
    val id = w.id
    val version = w.version
    val shortCode = w.shortCode
    val name = w.name
}

case class WarehouseCreateDto(shortCode: String, name: String, version: Int)