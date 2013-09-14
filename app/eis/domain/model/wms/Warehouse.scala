package eis.domain.model.wms

import com.wyb7.waffle.domain.entity.IdVersionEntity
import javax.persistence.{Column, Table, Entity}
import org.hibernate.annotations.NaturalId
import org.apache.commons.lang3.builder.{EqualsBuilder, HashCodeBuilder}

/**
 * User: abin
 * Date: 13-9-7
 * Time: 上午10:29
 */
@Entity
@Table(name="wms_warehouse")
class Warehouse extends IdVersionEntity {
    @NaturalId(mutable = true)
    var shortCode: String = _
    @Column(unique = true, nullable = false)
    var name:String = _

    var addresses:String = _

    var contactName: String = _
    var phoneNumber: String = _
    var fax: String = _
    var email: String = _

    override def hashCode():Int = {
        new HashCodeBuilder(17, 37)
                .append(name).toHashCode
    }


    override def equals(other: Any):Boolean = {
        if (other == null) return false
        if (this eq other.asInstanceOf[AnyRef]) return true
        if (!other.isInstanceOf[Warehouse]) return false
        val that = other.asInstanceOf[Warehouse]
        return new EqualsBuilder().append(name, that.name).isEquals
    }

    override def toString = shortCode + "-" + name

}
