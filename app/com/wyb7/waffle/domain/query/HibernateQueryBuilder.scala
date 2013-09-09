package com.wyb7.waffle.domain.query

import scala.collection.JavaConverters._
import com.wyb7.waffle.commons.util.JTypePredef._
import org.hibernate._
import org.apache.commons.lang3.StringUtils
import org.joda.time.{LocalTime, DateTime, LocalDateTime, LocalDate}
import org.slf4j.LoggerFactory
import java.io.Serializable
import scala.collection
import scala.Some

/**
 * Author: Wang Yibin
 * Date: 12-6-8
 * Time: 下午2:15
 */

class HibernateQueryBuilder(session: Session) {
    private val logger = LoggerFactory.getLogger(getClass)

    private var whereFragments = List.empty[String]
    private var paramMap = Map.empty[String, Any]

    def build: Query = {
        val hql = new StringBuilder("select %s from %s ".format(selectFragment, fromFragment))

        val whereString = whereFragments.filter(StringUtils.isNotBlank(_)).mkString(" and ")
        if (StringUtils.isNotBlank(whereString)) {
            hql.append(" where %s".format(whereString))
        }

        if (StringUtils.isNotBlank(orderByFragment)) {
            hql.append(" order by %s".format(orderByFragment))
        }
        if (StringUtils.isNotBlank(groupByFragment)) {
            hql.append(" group by %s".format(groupByFragment))
        }
        val query = session.createQuery(hql.toString())

        paramMap.foreach(e => {
            val (name, value) = e
            value match {
                case x: String => query.setString(name, x)
                case x: Long => query.setLong(name, x)
                case x: Boolean => query.setBoolean(name, x)
                case x: Int => query.setInteger(name, x)
                case x: BigDecimal => query.setBigDecimal(name, new java.math.BigDecimal(x.toString()))
                case x: Iterable[_] => query.setParameterList(name, x.asJavaCollection)
                case x: LocalDate => query.setDate(name, x.toDate)
                case x: LocalDateTime => query.setTimestamp(name, x.toDate)
                case x: DateTime => query.setTimestamp(name, x.toDate)
                case x: java.sql.Date => query.setDate(name, x)
                case x: java.util.Date => query.setTimestamp(name, x)
                case x => query.setParameter(name, x)

            }
        })

        query
    }


    private var selectFragment: String = _

    private var fromFragment: String = _
    private var orderByFragment: String = _
    private var groupByFragment: String = _

    private def addWhere(queryFragment: String, params: Iterable[(String, Any)]) {
        whereFragments ::= queryFragment
        paramMap ++= params
    }

    override def toString = {
        val hql = new StringBuilder("\n\tselect %s \n\tfrom %s ".format(selectFragment, fromFragment))
        val whereString = whereFragments.filter(StringUtils.isNotBlank(_)).mkString(" \n\t\tand ")
        if (StringUtils.isNotBlank(whereString)) {
            hql.append("\n\twhere %s".format(whereString))
        }

        if (StringUtils.isNotBlank(orderByFragment)) {
            hql.append("\n\torder by %s".format(orderByFragment))
        }
        if (StringUtils.isNotBlank(groupByFragment)) {
            hql.append("\n\tgroup by %s".format(groupByFragment))
        }
        hql.append("\nParams: ").append(paramMap.toString)
        hql.toString()
    }
}

class WrappedHibernateQuery(query: Query) {
    def uniqueResultOpt[T]: Option[T] = {
        val result = query.uniqueResult().asInstanceOf[T]
        if (result == null) None else Some(result)
    }
    def listNotEmpty[T](firstResult:Int = 0, maxResults:Int = 0):List[T] = {
        query.setFirstResult(firstResult)
        if(maxResults > 0) {
            query.setMaxResults(maxResults)
        }
        val l = query.list().asInstanceOf[JList[T]]
        if (l == null) List.empty[T] else l.asScala.toList
    }
}

class WrappedHibernateCriteria(criteria: Criteria) {
    def uniqueResultOpt[T]: Option[T] = {
        val result = criteria.uniqueResult().asInstanceOf[T]
        if (result == null) None else Some(result)
    }
    def listNotEmpty[T](firstResult:Int = 0, maxResults:Int = 0):List[T] = {
        criteria.setFirstResult(firstResult)
        if(maxResults > 0) {
            criteria.setMaxResults(maxResults)
        }
        val l = criteria.list().asInstanceOf[JList[T]]
        if (l == null) List.empty[T] else l.asScala.toList
    }
}

object HibernateQueryBuilder {
    def createQueryBuilder(session: Session): HibernateQueryBuilder = new HibernateQueryBuilder(session)

    def select(fragment: String)(implicit builder: HibernateQueryBuilder) {
        builder.selectFragment = fragment
    }
    def from(fragment: String)(implicit builder: HibernateQueryBuilder) {
        builder.fromFragment = fragment
    }

    def where(queryFragment: String, param: (String, Any)*)(implicit builder: HibernateQueryBuilder) {
        if (StringUtils.isNotBlank(queryFragment)) {
            builder.addWhere(queryFragment, param)
        }

    }
    def where(queryFragment: String, param: Any)(implicit builder: HibernateQueryBuilder) {
        val names = (""":\w*""".r findAllIn queryFragment).toList
        if (names.isEmpty) {
            throw new IllegalArgumentException("查询条件[%s]中没有找到命名参数".format(queryFragment))
        }
        where(queryFragment, names.map(_.substring(1) -> param):_*)
    }

    def orderBy(fragment: String)(implicit builder: HibernateQueryBuilder) {
        builder.orderByFragment = fragment
    }
    def groupBy(fragment: String)(implicit builder: HibernateQueryBuilder) {
        builder.groupByFragment = fragment
    }

    def orBuilder(implicit builder: HibernateQueryBuilder) = new OrBuilder(builder)

    implicit def wrappedQuery(query: Query):WrappedHibernateQuery = new WrappedHibernateQuery(query)
    implicit def wrappedCriteria(criteria: Criteria):WrappedHibernateCriteria = new WrappedHibernateCriteria(criteria)

    implicit class WrappedSession(session: Session) {
        def loadOpt[T](entityClass: Class[_], id: Serializable): Option[T] = {
            val result = session.load(entityClass, id)
            if (result == null) { None } else { Some(result.asInstanceOf[T]) }
        }

        def getOpt[T](entityClass: Class[_], id: Serializable): Option[T] = {
            val result = session.get(entityClass, id)
            if (result == null) { None } else { Some(result.asInstanceOf[T]) }
        }
    }

    implicit class WrappedSimpleNaturalIdLoadAccess(simpleNaturalIdLoadAccess: SimpleNaturalIdLoadAccess) {
        def loadOpt[T](naturalIdValue: AnyRef) = {
            val result = simpleNaturalIdLoadAccess.load(naturalIdValue).asInstanceOf[T]
            if (result == null) None else Some(result)
        }
    }

    protected class OrBuilder(builder: HibernateQueryBuilder) {
        private var statement = List.empty[String]
        private var paramMap = Map.empty[String, Any]

        def or(queryFragment: String, params: (String, Any)*) {
            statement ::= queryFragment
            paramMap ++= params
        }

        def build  {
            if (!statement.isEmpty) {
                HibernateQueryBuilder.where("(" + statement.mkString(" or ") + ")", paramMap.toList:_*)(builder)
            }
        }
    }

    def like(str: String) = "%" + str + "%"
}

