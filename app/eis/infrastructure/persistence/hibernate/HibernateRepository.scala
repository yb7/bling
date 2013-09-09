package eis.infrastructure.persistence.hibernate

import org.hibernate.Session
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.{Autowired, Required}
import com.wyb7.waffle.domain.query.HibernateQueryBuilder._
//import scala.reflect.runtime.{universe => ru}
import scala.reflect.ClassTag

/**
 * Functionality common to all Hibernate repositories.
 */
abstract class HibernateRepository {


    private var sessionFactory:SessionFactory = _

    type EntityType

    def runtimeClass: Class[_]

    @Autowired
    def setSessionFactory(sessionFactory: SessionFactory) {
        this.sessionFactory = sessionFactory
    }

    def getSession: Session = sessionFactory.getCurrentSession()

}


trait Save { this: HibernateRepository =>
    def save(obj: EntityType): Long = {
        getSession.save(obj).asInstanceOf[Long]
    }
}

trait Update { this: HibernateRepository =>
    def update(obj: EntityType) {
        getSession.update(obj)
    }
}

trait FindById { this: HibernateRepository =>
    def findById(id: Long): Option[EntityType] = {
        getSession.getOpt(runtimeClass, id)
    }
}
trait FindAll { this: HibernateRepository =>
    def findAll(): List[EntityType] = {
        getSession.createCriteria(runtimeClass).listNotEmpty()
    }
}

trait Delete { this: HibernateRepository =>
    def delete(id: Long) {
        getSession.delete(getSession.load(runtimeClass, id))
    }
}