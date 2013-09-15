package controllers.eis.article

import org.springframework.stereotype.Component
import play.api.mvc._
import controllers.support.JacksonJsonSupport
import com.wyb7.waffle.commons.value.Pagination
import org.springframework.beans.factory.annotation.Autowired
import eis.application.article.ArticleService
import eis.domain.model.article.ArticleQueryObject
import controllers.eis.wms.ArticleDto

/**
 * User: abin
 * Date: 13-9-7
 * Time: 下午1:32
 */
@Component
class ArticleController @Autowired()(articleService: ArticleService) extends Controller with JacksonJsonSupport {
    def query() = Action { implicit request =>
        val obj = new ArticleQueryObject
        obj.warehouseId = request.getQueryString("warehouseId").map(_.toLong)
        val start = request.getQueryString("start").map(_.toInt).getOrElse(0)
        val limit = request.getQueryString("limit").map(_.toInt).getOrElse(0)
        val articles = articleService.query(obj, start, limit).map(new ArticleDto(_))
        val count = articleService.count(obj)
        OkJson(Pagination.create(total = count, start = start, data = articles))
    }
}