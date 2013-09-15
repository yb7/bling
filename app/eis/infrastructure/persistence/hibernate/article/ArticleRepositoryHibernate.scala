package eis.infrastructure.persistence.hibernate.article

import eis.infrastructure.persistence.hibernate._
import eis.domain.model.article.{ArticleQueryObject, ArticleRepository, Article}
import org.springframework.stereotype.Repository
import com.wyb7.waffle.domain.query.HibernateQueryBuilder._
import org.springframework.beans.factory.annotation.Autowired
import eis.domain.model.foundation.{BizCodePrefix, BizCodeRepository}

/**
 * User: abin
 * Date: 13-9-13
 * Time: 上午9:02
 */
@Repository
class ArticleRepositoryHibernate @Autowired()(bizCodeRepository: BizCodeRepository) extends HibernateRepository with ArticleRepository
        with Save with Delete with Update with FindById {
    type EntityType = Article

    def runtimeClass: Class[_] = classOf[Article]

    private def createQuery(selectStr: String, queryObj: ArticleQueryObject) = {
        implicit val builder = createQueryBuilder(getSession)
        select(selectStr)
        from(classOf[Article].getSimpleName + " as a")
        for (x <- queryObj.warehouseId){
            where("a.warehouse.id = :warehouseId", x)
        }
        builder.build
    }


    override def save(obj: Article): Long = {
        obj.code = bizCodeRepository.nextBizCode(BizCodePrefix.Article)
        super.save(obj)
    }

    def query(queryObj: ArticleQueryObject, firstResult:Int = 0, maxResults:Int = 0): List[Article] = {
        createQuery("a", queryObj).listNotEmpty(firstResult, maxResults)
    }
    def count(queryObj: ArticleQueryObject): Long = {
        createQuery("count(a.id)", queryObj).uniqueResultOpt[Long].getOrElse(0)
    }
}
