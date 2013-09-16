package eis.domain.model.wms

import javax.persistence._
import com.wyb7.waffle.domain.entity.{StringEnumUserType, IdVersionEntity}
import org.hibernate.annotations.{TypeDef, TypeDefs, Type}
import org.joda.time.LocalDate
import com.wyb7.waffle.commons.util.JTypePredef._
import eis.domain.model.article.Article
import collection.JavaConverters._

/**
 * User: abin
 * Date: 13-9-15
 * Time: 上午8:17
 */
//@TypeDefs(
//    Array(new TypeDef(name = "redOrBlue", typeClass = classOf[RedOrBlue])
//))
@Entity
@Table(name = "wms_outward_processing")
class OutwardProcessing extends IdVersionEntity {
    var bizCode: String = _

    // 只能是 红字 或者 蓝字
    var redOrBlue: String = _

    @Type(`type`="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    var outwardDate: LocalDate = _

    @Type(`type`="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    var expectedCompletionDate: LocalDate = _

    @ManyToMany
    @JoinTable(name = "wms_outward_processing_articles",
        joinColumns = Array(new JoinColumn(name = "distribution_allocation_id")),
        inverseJoinColumns = Array(new JoinColumn(name = "article_id"))
    )
    protected var articles:JSet[Article] = new JHashSet[Article]

    def addArticle(article: Article) {
        this.articles.add(article)
    }
    def removeArticle(article: Article) {
        this.articles.remove(article)
    }

    def listArticles = articles.asScala.toSet

    @ManyToOne(fetch = FetchType.EAGER)
    var receiveWarehouse: Warehouse = _

    var remark: String = _
}

//object RedOrBlue extends Enumeration {
//    val RED, BLUE = Value
//}

class RedOrBlue extends StringEnumUserType[RedOrBlue]