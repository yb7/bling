package controllers.eis.foundation

import org.springframework.stereotype.Component
import play.api.mvc._
import eis.interfaces.foundation.facade.{CompanyCreateDto, CompanyServiceFacade}
import controllers.support.JacksonJsonSupport
import com.wyb7.waffle.commons.value.Pagination
import org.springframework.beans.factory.annotation.Autowired

/**
 * User: abin
 * Date: 13-9-7
 * Time: 下午1:32
 */
@Component
class CompanyController @Autowired()(companyFacade: CompanyServiceFacade) extends Controller with JacksonJsonSupport {
    def query() = Action { implicit request =>
        Ok(toJson(Pagination.createWithAllData(companyFacade.query())))
    }

    def create() = Action(jsonParser[CompanyCreateDto]) { implicit request =>
        Ok(toJson(companyFacade.create(request.body)))
    }
}
