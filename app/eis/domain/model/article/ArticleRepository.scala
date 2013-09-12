package eis.domain.model.article

/**
 * User: abin
 * Date: 13-9-12
 * Time: 下午9:39
 */
trait ArticleRepository {
    def save(article: Article): Long
}
