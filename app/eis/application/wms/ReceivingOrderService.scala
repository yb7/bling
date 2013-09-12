package eis.application.wms

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import eis.domain.model.wms.{ReceivingOrderRepository, ReceivingOrder}
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import eis.domain.model.article.ArticleRepository

/**
 * User: abin
 * Date: 13-9-12
 * Time: 下午8:45
 */
trait ReceivingOrderService {
    def importByXls(order: ReceivingOrder, workbook: HSSFWorkbook): List[String]
    def createNew(): ReceivingOrder
    def findById(id: Long): Option[ReceivingOrder]
}

@Component
class ReceivingOrderServiceImpl extends ReceivingOrderService {
    private var articleXlsReader: ArticleXlsReader = _
    @Autowired
    def setArticleXlsReader(reader: ArticleXlsReader) {
        articleXlsReader = reader
    }
    private var articleRepository: ArticleRepository = _
    @Autowired
    def setArticleRepository(articleRepository: ArticleRepository) {
        this.articleRepository = articleRepository
    }
    private var receivingOrderRepository: ReceivingOrderRepository = _
    @Autowired
    def setArticleRepository(receivingOrderRepository: ReceivingOrderRepository) {
        this.receivingOrderRepository = receivingOrderRepository
    }

    def importByXls(order: ReceivingOrder, workbook: HSSFWorkbook): List[String] = {
        articleXlsReader.read(workbook) match {
            case Right(articles) => articles.foreach {article =>
                    order.addArticle(article)
                    article.receivingOrder = order
                    articleRepository.save(article)
                }
                List.empty[String]
            case Left(messages) => messages
        }

    }

    def createNew(): ReceivingOrder = {
        val order = new ReceivingOrder
        this.receivingOrderRepository.save(order)
        order
    }

    def findById(id: Long): Option[ReceivingOrder] = {
        this.receivingOrderRepository.findById(id)
    }
}
