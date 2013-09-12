package eis.infrastructure.persistence.hibernate.wms

import eis.domain.model.foundation.{BizCodePrefix, BizCodeRepository, Company, CompanyRepository}
import eis.domain.model.wms.{ReceivingOrderRepository, ReceivingOrder}
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
class ReceivingOrderRepositoryHibernate @Autowired()(bizCodeRepository: BizCodeRepository) extends HibernateRepository
        with ReceivingOrderRepository
        with Update
        with FindAll
        with FindById
        with Delete {
    type EntityType = ReceivingOrder

    def runtimeClass: Class[_] = classOf[ReceivingOrder]

    def save(order: ReceivingOrder): Long = {
        order.bizCode = bizCodeRepository.nextBizCode(BizCodePrefix.ReceivingOrder)
        getSession.save(order).asInstanceOf[Long]
    }
}