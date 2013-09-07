package eis.infrastructure.persistence.hibernate.foundation

import eis.domain.model.foundation.{Company, CompanyRepository}
//import com.wyb7.waffle.domain.query.HibernateQueryBuilder._
import eis.infrastructure.persistence.hibernate._
import org.springframework.stereotype.Repository

/**
 * User: abin
 * Date: 13-9-7
 * Time: 上午11:01
 */
@Repository
class CompanyRepositoryHibernate extends HibernateRepository
        with CompanyRepository with Save
        with Update
        with FindAll
        with FindById {
    type EntityType = Company

    def runtimeClass: Class[_] = classOf[Company]
}