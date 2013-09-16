package eis.application.wms

import eis.domain.model.wms.{WarehouseRepository, OutwardProcessing, OutwardProcessingRepository}
import eis.domain.model.article.{ArticleRepository, Article}
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import controllers.eis.wms.OutwardProcessingExecuteDto

/**
 * User: abin
 * Date: 13-9-15
 * Time: 上午8:15
 */
trait OutwardProcessingService {
    def createNew(): OutwardProcessing
    def articles(id: Long): Set[Article]
    def findAll(): List[OutwardProcessing]
    def execute(id: Long, dto: OutwardProcessingExecuteDto): Unit
    def addArticles(orderId: Long, articleIds: Set[Long])
    def deleteArticle(orderId: Long, articleIds: Set[Long])
}

@Transactional
@Component
class OutwardProcessingServiceImpl extends OutwardProcessingService {
    private var outwardProcessingRepository: OutwardProcessingRepository= _
    @Autowired
    def setOutwardProcessingRepository(OutwardProcessingRepository: OutwardProcessingRepository) {
        this.outwardProcessingRepository = OutwardProcessingRepository
    }
    private var warehouseRepository: WarehouseRepository = _
    @Autowired
    def setWarehouseRepository(warehouseRepository: WarehouseRepository) {
        this.warehouseRepository = warehouseRepository
    }
    private var articleRepository: ArticleRepository = _
    @Autowired
    def setArticleRepository(articleRepository: ArticleRepository) {
        this.articleRepository = articleRepository
    }

    def createNew(): OutwardProcessing = {
        val order = new OutwardProcessing
        this.outwardProcessingRepository.save(order)
        order
    }

    @Transactional(readOnly = true)
    def articles(id: Long): Set[Article] = {
        this.outwardProcessingRepository.findById(id).get.listArticles
    }

    @Transactional(readOnly = true)
    def findAll(): List[OutwardProcessing] = {
        this.outwardProcessingRepository.findAll()
    }

    def execute(id: Long, dto: OutwardProcessingExecuteDto) {
        val order = this.outwardProcessingRepository.findById(id).get
        order.outwardDate = dto.outwardDate
        order.expectedCompletionDate = dto.expectedCompletionDate
        order.redOrBlue = dto.redOrBlue
//        order.sendWarehouse = warehouseRepository.findById(dto.sendWarehouseId).get
        order.receiveWarehouse = warehouseRepository.findById(dto.receiveWarehouseId).get
        order.remark = dto.remark
        order.listArticles.foreach {article =>
            article.warehouse = order.receiveWarehouse
            this.articleRepository.update(article)
        }
        this.outwardProcessingRepository.update(order)
    }

    def addArticles(orderId: Long, articleIds: Set[Long]) {
        val order = this.outwardProcessingRepository.findById(orderId).get
        articleIds.map(this.articleRepository.findById(_).get).foreach(order.addArticle(_))
        this.outwardProcessingRepository.update(order)
    }

    def deleteArticle(orderId: Long, articleIds: Set[Long]) {
        val order = this.outwardProcessingRepository.findById(orderId).get
        articleIds.map(this.articleRepository.findById(_).get).foreach(order.removeArticle(_))
        this.outwardProcessingRepository.update(order)
    }
}
