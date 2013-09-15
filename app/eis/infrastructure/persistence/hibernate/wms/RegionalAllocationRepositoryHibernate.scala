package eis.infrastructure.persistence.hibernate.wms

import eis.domain.model.foundation.{BizCodePrefix, BizCodeRepository}
import eis.domain.model.wms.{RegionalAllocation, RegionalAllocationRepository}
import org.springframework.beans.factory.annotation.Autowired

//import com.wyb7.waffle.domain.query.HibernateQueryBuilder._
import eis.infrastructure.persistence.hibernate._
import org.springframework.stereotype.Repository

/**
 * User: abin
 * Date: 13-9-7
 * Time: 上午11:01
 */
@Repository
class RegionalAllocationRepositoryHibernate @Autowired()(bizCodeRepository: BizCodeRepository) extends HibernateRepository
        with RegionalAllocationRepository
        with Update
        with FindAll
        with FindById
        with Delete {
    type EntityType = RegionalAllocation

    def runtimeClass: Class[_] = classOf[RegionalAllocation]

    def save(order: RegionalAllocation): Long = {
        order.bizCode = bizCodeRepository.nextBizCode(BizCodePrefix.RegionalAllocation)
        getSession.save(order).asInstanceOf[Long]
    }
}