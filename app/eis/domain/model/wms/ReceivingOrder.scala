package eis.domain.model.wms

import javax.persistence._
import com.wyb7.waffle.domain.entity.IdVersionEntity
import org.hibernate.annotations.{FetchMode, Fetch, Type}
import org.joda.time.LocalDateTime
import eis.domain.model.article.Article
import com.wyb7.waffle.commons.util.JTypePredef._
import scala.collection.JavaConverters._

/**
 * User: abin
 * Date: 13-9-12
 * Time: 下午5:54
 */
@Entity
@Table(name = "wms_receiving_order")
class ReceivingOrder extends IdVersionEntity {
    var bizCode: String = _

    @Type(`type`="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    var receivingTime: LocalDateTime = _

    @OneToMany(mappedBy = "receivingOrder")
    @Fetch(FetchMode.SUBSELECT)
    protected var articles:JCollection[Article] = new JArrayList[Article]

    def addArticle(article: Article) {
        this.articles.add(article)
    }

    def listArticles = articles.asScala.toList
}
