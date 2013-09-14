package eis.application.wms

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import eis.domain.model.wms.{WarehouseRepository, ReceivingOrderRepository, ReceivingOrder}
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import eis.domain.model.article.{Article, ArticleRepository}
import org.springframework.transaction.annotation.Transactional
import com.wyb7.waffle.commons.xls.ErrorMessage
import org.hibernate.Hibernate
import controllers.eis.wms.ReceivingExecuteDto

/**
 * User: abin
 * Date: 13-9-12
 * Time: 下午8:45
 */
trait ReceivingOrderService {
    def importByXls(orderId: Long, workbook: HSSFWorkbook): ErrorMessage
    def createNew(): ReceivingOrder
    def findById(id: Long): Option[ReceivingOrder]
    def findAll(): List[ReceivingOrder]
    def articles(id: Long): List[Article]
    def deleteArticle(articleId: Long)
    def execute(id: Long, dto: ReceivingExecuteDto): ReceivingOrder
}

@Transactional
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
    def setReceivingOrderRepository(receivingOrderRepository: ReceivingOrderRepository) {
        this.receivingOrderRepository = receivingOrderRepository
    }
    private var warehouseRepository: WarehouseRepository = _
    @Autowired
    def setWarehouseRepository(warehouseRepository: WarehouseRepository) {
        this.warehouseRepository = warehouseRepository
    }

    def importByXls(orderId: Long, workbook: HSSFWorkbook): ErrorMessage = {
        val order = findById(orderId).get
        articleXlsReader.read(workbook) match {
            case Right(articles) => articles.foreach {article =>
                    order.addArticle(article)
                    article.receivingOrder = order
                    articleRepository.save(article)
                }
                ErrorMessage.NoError
            case Left(messages) => messages
        }

    }

    def createNew(): ReceivingOrder = {
        val order = new ReceivingOrder
        this.receivingOrderRepository.save(order)
        order
    }

    @Transactional(readOnly = true)
    def findById(id: Long): Option[ReceivingOrder] = {
        this.receivingOrderRepository.findById(id)
    }
    @Transactional(readOnly = true)
    def findAll(): List[ReceivingOrder] = {
        this.receivingOrderRepository.findAll()
    }
    @Transactional(readOnly = true)
    def articles(id: Long): List[Article] = {
        val articles = this.receivingOrderRepository.findById(id).get.listArticles
        Hibernate.initialize(articles)
        articles
    }
    def deleteArticle(articleId: Long) {
        this.articleRepository.delete(articleId)
    }

    def execute(id: Long, dto: ReceivingExecuteDto): ReceivingOrder = {
        val order = this.receivingOrderRepository.findById(id).get
        order.receivingDate = dto.receivingDate
        order.warehouse = warehouseRepository.findById(dto.warehouseId).get
        this.receivingOrderRepository.update(order)
        order
    }
}
