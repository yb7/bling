package eis.application.article

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import eis.domain.model.article.{ArticleQueryObject, ArticleRepository, Article}

/**
 * User: abin
 * Date: 13-9-7
 * Time: 上午11:31
 */


trait ArticleService {
    def query(queryObj: ArticleQueryObject, firstResult:Int = 0, maxResults:Int = 0): List[Article]
    def count(queryObj: ArticleQueryObject): Long
}

@Transactional
@Component
class ArticleServiceImpl extends ArticleService {

    private var articleRepository: ArticleRepository = _

    @Autowired
    def setArticleRepository(repo: ArticleRepository) {
        articleRepository = repo
    }

    @Transactional(readOnly = true)
    def query(queryObj: ArticleQueryObject, firstResult:Int = 0, maxResults:Int = 0): List[Article] =  {
        articleRepository.query(queryObj, firstResult, maxResults)
    }
    @Transactional(readOnly = true)
    def count(queryObj: ArticleQueryObject): Long = {
        articleRepository.count(queryObj)
    }
}
