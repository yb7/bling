package util

import javax.persistence.Transient
import domain.hibernate.util.HibernateSessionAware

trait HibernateSessionHolder extends HibernateSessionAware {
    @Transient
    override def sessionFactory = HibernateUtil.sessionFactory

}
