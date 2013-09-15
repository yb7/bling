package eis.domain.model.article

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility

/**
 * User: abin
 * Date: 13-9-12
 * Time: 下午9:39
 */
trait ArticleRepository {
    def save(article: Article): Long
    def delete(articleId: Long): Unit
    def update(article: Article): Unit
    def findById(id: Long): Option[Article]
    def query(queryObj: ArticleQueryObject, firstResult:Int = 0, maxResults:Int = 0): List[Article]
    def count(queryObj: ArticleQueryObject): Long
}
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class ArticleQueryObject {
    var warehouseId: Option[Long] = None
}
