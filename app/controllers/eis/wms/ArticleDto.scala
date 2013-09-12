package controllers.eis.wms

import com.fasterxml.jackson.annotation.{JsonIgnore, JsonAutoDetect}
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility
import eis.domain.model.article.Article
import com.wyb7.waffle.commons.util.SafeGetter._

/**
 * User: abin
 * Date: 13-9-12
 * Time: 下午10:02
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class ArticleDto(_article: Article) {
    @JsonIgnore
    private val article = _article
    val code: String = article.code
    val vendor1Id: Long = nullSafe(article.vendor1.id)
    val vendor1Name: String = article.vendor1.name
    val vendor2Id: Long = nullSafe(article.vendor2.id)
    val vendor2Name: String = article.vendor2.name
    val vendor3Id: Long = nullSafe(article.vendor3.id)
    val vendor3Name: String = article.vendor3.name
    val vendorArticleName: String = article.vendorArticleName
    val vendorArticleNumber: String = article.vendorArticleNumber
    val name: String = article.name
    //    @ManyToOne(fetch=FetchType.LAZY, optional = false) ArticleCategory
    val category: String = article.category
    val style: String = article.style
    val lotNumber: String = article.lotNumber
    val styleNumber: String  = article.styleNumber
    // 手寸
    val handInch: String = article.handInch
    val metalName: String = article.metalName
    val metalPurity: String  = article.metalPurity
    val weight: BigDecimal = article.weight
    val certificateWeight: BigDecimal = article.certificateWeight
    val tagName: String = article.tagName
    val pricingMethod: String = article.pricingMethod
    val certificateCode: String = article.certificateCode
    val goldWeight: BigDecimal = article.goldWeight
    val goldPrice: BigDecimal = article.goldPrice
    val remark: String = article.remark
    // 镶口
    val rabbet: String = article.rabbet
    // 宝石名称
    val gemName: String = article.gemName
    val gemCode: String  = article.gemCode
    val gemColor: String = article.gemColor
    val gemPurity: String = article.gemPurity
    val gemCut: String  = article.gemCut
    val gemCount: Int = article.gemCount
    val gemWeight: BigDecimal = article.gemWeight
    val gemRemark: String = article.gemRemark
    val sideStoneName: String = article.sideStoneName
    val sideStoneCount: Int = article.sideStoneCount
    val sideStoneWeight: BigDecimal = article.sideStoneWeight
    val sideStoneCode: String = article.sideStoneCode

    val procurementSettlementPrice1: BigDecimal = article.procurementSettlementPrice1
    val procurementSettlementPrice2: BigDecimal = article.procurementSettlementPrice2
    val procurementSettlementPrice3: BigDecimal = article.procurementSettlementPrice3
    val retailPrice: BigDecimal = article.retailPrice

    val settlementMode: String = article.settlementMode
    val eCommerceNumber: String = article.eCommerceNumber
}
