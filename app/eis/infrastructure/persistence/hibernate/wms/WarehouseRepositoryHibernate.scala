package eis.infrastructure.persistence.hibernate.wms

import eis.domain.model.foundation.{Company, CompanyRepository}
import org.hibernate.criterion.Restrictions
import eis.domain.model.wms.{WarehouseRepository, Warehouse}

//import com.wyb7.waffle.domain.query.HibernateQueryBuilder._
import eis.infrastructure.persistence.hibernate._
import org.springframework.stereotype.Repository
import com.wyb7.waffle.domain.query.HibernateQueryBuilder._

/**
 * User: abin
 * Date: 13-9-7
 * Time: 上午11:01
 */
@Repository
class WarehouseRepositoryHibernate extends HibernateRepository
        with WarehouseRepository with Save
        with Update
        with FindAll
        with FindById
        with Delete {
    type EntityType = Warehouse

    def runtimeClass: Class[_] = classOf[Warehouse]
}