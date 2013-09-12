package eis.infrastructure.persistence.hibernate.foundation

import org.springframework.stereotype.Repository
import eis.domain.model.foundation.{BizCode, BizCodePrefix, BizCodeRepository}
import java.util.Date
import org.hibernate.LockOptions
import java.text.SimpleDateFormat
import org.springframework.transaction.annotation.{Propagation, Transactional}
import eis.infrastructure.persistence.hibernate.HibernateRepository
import com.wyb7.waffle.commons.util.CommonPredef._
/**
 * User: abin
 * Date: 13-9-12
 * Time: 下午6:11
 */
@Repository
class BizCodeRepositoryHibernate extends HibernateRepository with BizCodeRepository {


    type EntityType = BizCode

    def runtimeClass: Class[_] = classOf[BizCode]

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    def nextBizCode(buzCodeType:BizCodePrefix.Value) : String = {
        val prefix: String = buzCodeType + formatDateForBuzzCode(new Date)
        val nextV = nextValue(prefix)
        prefix + formatByBuzzCodePrefix(buzCodeType, nextV)
    }

    private def nextValue(prefix: String): Long = {
        var buzzCode: BizCode = getSession.bySimpleNaturalId(classOf[BizCode]).`with`(LockOptions.UPGRADE)//LockMode.PESSIMISTIC_WRITE)
                .load(prefix).asInstanceOf[BizCode]

        if (buzzCode == null) {
            buzzCode = new BizCode(prefix)
        }
        val v = buzzCode.getNextValue
        buzzCode.increaseNextValue
        getSession.saveOrUpdate(buzzCode)
        v
    }

    private def formatByBuzzCodePrefix(prefix:BizCodePrefix.Value, nextValue: Long) : String = {
//        if (prefix == BuzzCodePrefix.Cargo || prefix == BizCodePrefix.Pallet) {
//            nextValue.formatWithPrefix0(7)
//        } else {
            nextValue.formatWithPrefix0(4)
//        }
    }



    private def formatDateForBuzzCode(date:Date):String = {
        (new SimpleDateFormat("yyMMdd")).format(date);
    }

//    def isBizCode(str: String): Boolean = {
//        if (StringUtils.isBlank(str)) return false
//        for (prefix <- BuzzCodePrefix.values) {
//            if (str.startsWith(prefix.toString)) {
//                return true
//            }
//        }
//        return false
//    }
}
