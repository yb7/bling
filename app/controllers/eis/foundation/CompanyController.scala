package controllers.eis.foundation

import org.springframework.stereotype.Component
import play.api.mvc._
import controllers.support.JacksonJsonSupport
import com.wyb7.waffle.commons.value.Pagination
import org.springframework.beans.factory.annotation.Autowired
import eis.application.foundation.CompanyService
import controllers.eis.foundation.assembler.CompanyDtoAssembler
import eis.domain.model.foundation.Company
import com.wyb7.waffle.commons.value.GenericActionResult._

/**
 * User: abin
 * Date: 13-9-7
 * Time: 下午1:32
 */
@Component
class CompanyController @Autowired()(companyService: CompanyService) extends Controller with JacksonJsonSupport {
    def query() = Action { implicit request =>
        OkJson(Pagination.createWithAllData(companyService.query().map(CompanyDtoAssembler.toDto(_))))
    }

    def create() = Action(jsonParser[CompanyCreateDto]) { implicit request =>
        val dto = request.body
        val domain = new Company
        domain.name = dto.name
        domain.shortCode = dto.shortCode
        companyService.createNew(domain)
        CompanyDtoAssembler.toDto(domain)
        OkJson(successResult(CompanyDtoAssembler.toDto(domain)))
    }

    def update(id: Long) = Action(jsonParser[CompanyCreateDto]) { implicit request =>
        val dto = request.body
        companyService.findById(id) match {
            case Some(company) => company.name = dto.name
                company.shortCode = dto.shortCode
                companyService.update(company)
                OkJson(successResult(CompanyDtoAssembler.toDto(company)))
            case None => NotFound
        }
    }

    def delete(id: Long) = Action { implicit request =>
        companyService.delete(id)
        OkJson(successResult(s"删除公司 ID[$id]"))

    }
}

case class CompanyDto(id: Long, shortCode: String, name: String, version: Int)

case class CompanyCreateDto(shortCode: String, name: String, version: Int)