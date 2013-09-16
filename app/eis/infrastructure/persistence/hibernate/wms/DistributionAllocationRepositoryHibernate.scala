package eis.infrastructure.persistence.hibernate.wms

import eis.domain.model.foundation.{BizCodePrefix, BizCodeRepository}
import eis.domain.model.wms.{DistributionAllocation, DistributionAllocationRepository}
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
class DistributionAllocationRepositoryHibernate @Autowired()(bizCodeRepository: BizCodeRepository) extends HibernateRepository
        with DistributionAllocationRepository
        with Update
        with FindAll
        with FindById
        with Delete {
    type EntityType = DistributionAllocation

    def runtimeClass: Class[_] = classOf[DistributionAllocation]

    def save(order: DistributionAllocation): Long = {
        order.bizCode = bizCodeRepository.nextBizCode(BizCodePrefix.DistributionAllocation)
        getSession.save(order).asInstanceOf[Long]
    }
}