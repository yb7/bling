package eis.domain.model.foundation

import javax.persistence.{Table, Entity}
import com.wyb7.waffle.domain.entity.IdVersionEntity
import org.hibernate.annotations.NaturalId

/**
 * User: abin
 * Date: 13-9-12
 * Time: 下午6:07
 */
@Entity
@Table(name = "fnd_biz_code")
class BizCode(tPrefix: String, tNextValue: Long = 1) extends IdVersionEntity {
    protected def this() = this("")

    @NaturalId
    protected var prefix:String = tPrefix

    protected var nextValue:Long = tNextValue

    def getNextValue = nextValue

    def increaseNextValue() = {
        this.nextValue += 1
    }

}

