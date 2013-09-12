package eis.application.wms

import eis.domain.model.article.Article
import org.slf4j.LoggerFactory
import com.wyb7.waffle.commons.xls.{RowMapper, ReadXlsToMap}
import org.apache.poi.hssf.usermodel.{HSSFCell, HSSFWorkbook}
import com.wyb7.waffle.commons.xls.HSSFCellHelper._
import eis.domain.model.foundation.{CompanyRepository, Company}
import com.wyb7.waffle.commons.util.CommonPredef._
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired

/**
 * User: abin
 * Date: 13-9-12
 * Time: 下午8:09
 */
trait ArticleXlsReader {
    def read(workbook: HSSFWorkbook): Either[List[String], List[Article]]
}



@Component
class ArticleXlsReaderImpl @Autowired()(companyRepository: CompanyRepository) extends ArticleXlsReader {
    protected val logger = LoggerFactory.getLogger(classOf[ArticleXlsReader])

    implicit private def hssfCellToCompany(cell: HSSFCell): Company = {
        cell.toString.notEmptyOp.flatMap(companyRepository.findByName(_)).getOrElse(null)
    }

    def read(workbook: HSSFWorkbook): Either[List[String], List[Article]] =  {
        val sheet = workbook.getSheetAt(0)
        val reader = new ReadXlsToMap(sheet, Set.empty[String])
        var rowCount = 1
        var messages = List.empty[String]
        var articles = List.empty[Article]
        reader.read(new RowMapper[Unit] {
            def map(values: Map[String, HSSFCell]) {
                rowCount += 1
                var warnMessages = List.empty[String]
                val article = new Article
                article.code = values("货品编码")
                article.vendor1 = values("供应商1")
                article.vendor2 = values("供应商2")
                article.vendor3 = values("供应商3")
                article.vendorArticleName = values("厂家品名")
                article.name = values("货品名称")
                article.category = values("货品品种")
                article.style = values("款式")
                article.vendorArticleNumber = values("厂家货号")
                article.lotNumber = values("批号")
                article.styleNumber = values("款号")
                article.handInch = values("手寸")
                article.metalName = values("金属名称")
                article.metalPurity = values("金属纯度")
                article.weight = values("货品重量")
                article.certificateWeight = values("证书重量")
                article.tagName = values("标签名称")
                article.pricingMethod = values("货品计价方式")
                article.certificateCode = values("证书号")
                article.remark = values("货品备注")
                article.goldWeight = values("金重量")
                article.goldPrice = values("金单价")
                article.rabbet = values("镶口")
                article.gemName = values("宝石名称")
                article.gemCode = values("宝石石号")
                article.gemColor = values("宝石颜色")
                article.gemPurity = values("宝石净度")
                article.gemCut = values("宝石切工")
                article.gemCount = values("宝石数量")
                article.gemWeight = values("宝石重量")
                article.gemRemark = values("宝石备注")
                article.sideStoneName = values("辅石名称")
                article.sideStoneCount = values("辅石数量")
                article.sideStoneWeight = values("辅石重量")
                article.sideStoneCode = values("辅石石号")
                article.retailPrice = values("零售价")
                article.procurementSettlementPrice1 = values("采购结算价1")
                article.procurementSettlementPrice2 = values("采购结算价2")
                article.procurementSettlementPrice3 = values("采购结算价3")
                article.settlementMode = values("结算模式")
                article.eCommerceNumber = values("电商系列号")

                if (warnMessages.isEmpty) {
                    articles :+= article
                } else {
                    messages :+= "第" + rowCount + "行: "+warnMessages.mkString(",")
                }

            }
        })
        if (messages.isEmpty) {
            Right(articles)
        } else {
            Left(messages)
        }
    }
}