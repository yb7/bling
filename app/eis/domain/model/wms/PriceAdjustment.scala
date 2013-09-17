package eis.domain.model.wms

import javax.persistence._
import com.wyb7.waffle.domain.entity.IdVersionEntity
import org.hibernate.annotations.Type
import org.joda.time.LocalDate
import com.wyb7.waffle.commons.util.JTypePredef._
import eis.domain.model.article.Article
import collection.JavaConverters._

/**
 * User: abin
 * Date: 13-9-15
 * Time: 上午8:17
 */
@Entity
@Table(name = "wms_price_adjustment")
class PriceAdjustment extends IdVersionEntity {
    var bizCode: String = _


    @Type(`type`="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    var effectiveDate: LocalDate = _

    @ManyToMany
    @JoinTable(name = "wms_price_adjustment_articles",
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

    var remark: String = _

    var retailPriceDirectly: BigDecimal = _

    var incrementBaseOnCost: BigDecimal = _
    var coefficientBaseOnCost: BigDecimal = _

    var incrementBaseOnRetailPrice: BigDecimal = _
    var coefficientBaseOnRetailPrice: BigDecimal = _

    var excludeUnits0: Boolean = false
    var excludeUnits1: Boolean = false
    var excludeUnits2: Boolean = false
    var excludeUnits3: Boolean = false
    var excludeUnits4: Boolean = false
    var excludeUnits5: Boolean = false
    var excludeUnits6: Boolean = false
    var excludeUnits7: Boolean = false
    var excludeUnits8: Boolean = false
    var excludeUnits9: Boolean = false

    def excludeUnits = {
        var units = List.empty[Int]
        if (excludeUnits0) units :+= 0
        if (excludeUnits1) units :+= 1
        if (excludeUnits2) units :+= 2
        if (excludeUnits3) units :+= 3
        if (excludeUnits4) units :+= 4
        if (excludeUnits5) units :+= 5
        if (excludeUnits6) units :+= 6
        if (excludeUnits7) units :+= 7
        if (excludeUnits8) units :+= 8
        if (excludeUnits9) units :+= 9
        units
    }
}