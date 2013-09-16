package eis.infrastructure.persistence.hibernate.wms

import eis.domain.model.foundation.{BizCodePrefix, BizCodeRepository}
import eis.domain.model.wms.{PriceAdjustment, PriceAdjustmentRepository}
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
class PriceAdjustmentRepositoryHibernate @Autowired()(bizCodeRepository: BizCodeRepository) extends HibernateRepository
        with PriceAdjustmentRepository
        with Update
        with FindAll
        with FindById
        with Delete {
    type EntityType = PriceAdjustment

    def runtimeClass: Class[_] = classOf[PriceAdjustment]

    def save(order: PriceAdjustment): Long = {
        order.bizCode = bizCodeRepository.nextBizCode(BizCodePrefix.PriceAdjustment)
        getSession.save(order).asInstanceOf[Long]
    }
}