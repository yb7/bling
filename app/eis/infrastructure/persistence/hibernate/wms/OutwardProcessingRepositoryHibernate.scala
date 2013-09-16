package eis.infrastructure.persistence.hibernate.wms

import eis.domain.model.foundation.{BizCodePrefix, BizCodeRepository}
import eis.domain.model.wms.{OutwardProcessing, OutwardProcessingRepository}
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
class OutwardProcessingRepositoryHibernate @Autowired()(bizCodeRepository: BizCodeRepository) extends HibernateRepository
        with OutwardProcessingRepository
        with Update
        with FindAll
        with FindById
        with Delete {
    type EntityType = OutwardProcessing

    def runtimeClass: Class[_] = classOf[OutwardProcessing]

    def save(order: OutwardProcessing): Long = {
        order.bizCode = bizCodeRepository.nextBizCode(BizCodePrefix.OutwardProcessing)
        getSession.save(order).asInstanceOf[Long]
    }
}