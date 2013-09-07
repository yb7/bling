package eis.domain.model.article

import javax.persistence.{ManyToOne, FetchType, Table, Entity}
import com.wyb7.waffle.domain.entity.IdVersionEntity
import eis.domain.model.foundation.Company

/**
 * User: abin
 * Date: 13-9-3
 * Time: 下午10:06
 */

@Entity
@Table(name = "article")
class Article extends IdVersionEntity {
    var code: String = _
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    var vendor1: Company = _
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    var vendor2: Company = _
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    var vendor3: Company = _
    var vendorArticleName: String = _
    var vendorArticleNumber: String = _
    var name: String = _
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    var category: ArticleCategory = _
    var style: String = _
    var lotNumber: String = _
    var styleNumber: String  = _
    // 手寸
    var handInch: String = _
    var metalName: String = _
    var metalPurity: String  = _
    var weight: BigDecimal = 0
    var certificateWeight: BigDecimal = 0
    var tagName: String = _
    var pricingMethod: String = _
    var certificateCode: String = _
    var goldWeight: BigDecimal = 0
    var goldPrice: BigDecimal = 0
    var remark: String = _
    // 镶口
    var rabbet: String = _
    // 宝石名称
    var gemName: String = _
    var gemCode: String  = _
    var gemColor: String = _
    var gemPurity: String = _
    var gemCut: String  = _
    var gemCount: Int = _
    var gemWeight: BigDecimal = _
    var gemRemark: String = _
    var sideStoneName: String = _
    var sideStoneCount: Int = _
    var sideStoneWeight: BigDecimal = _
    var sideStoneCode: String = _

    var procurementSettlementPrice1: BigDecimal = 0
    var procurementSettlementPrice2: BigDecimal = 0
    var procurementSettlementPrice3: BigDecimal = 0
    var retailPrice: BigDecimal = 0

}
