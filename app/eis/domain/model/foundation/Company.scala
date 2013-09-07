package eis.domain.model.foundation

import com.wyb7.waffle.domain.operation._


import com.wyb7.waffle.commons.util.JTypePredef._
import collection.JavaConverters._
import org.hibernate.criterion.Restrictions
import com.wyb7.waffle.domain.entity.IdVersionEntity
import org.apache.commons.lang3.builder.{EqualsBuilder, HashCodeBuilder}
import javax.persistence._
import util.HibernateSessionHolder
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.CascadeType
import scala.Some
import org.hibernate.annotations.{Fetch, NaturalId, FetchMode}


@Entity
@Table(name="fnd_company")
class Company extends IdVersionEntity {
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
        if (!other.isInstanceOf[Company]) return false
        val that = other.asInstanceOf[Company]
        return new EqualsBuilder().append(name, that.name).isEquals
    }

    override def toString = shortCode + "-" + name

}