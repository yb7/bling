package eis.infrastructure.persistence.hibernate.article

import eis.infrastructure.persistence.hibernate.{Update, Delete, Save, HibernateRepository}
import eis.domain.model.article.{ArticleRepository, Article}
import org.springframework.stereotype.Repository

/**
 * User: abin
 * Date: 13-9-13
 * Time: 上午9:02
 */
@Repository
class ArticleRepositoryHibernate extends HibernateRepository with ArticleRepository
        with Save with Delete with Update {
    type EntityType = Article

    def runtimeClass: Class[_] = classOf[Article]
}
