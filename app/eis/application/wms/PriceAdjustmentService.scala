package eis.application.wms

import eis.domain.model.wms.{WarehouseRepository, PriceAdjustment, PriceAdjustmentRepository}
import eis.domain.model.article.{ArticleRepository, Article}
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import controllers.eis.wms.PriceAdjustmentExecuteDto

/**
 * User: abin
 * Date: 13-9-15
 * Time: 上午8:15
 */
trait PriceAdjustmentService {
    def createNew(): PriceAdjustment
    def articles(id: Long): Set[Article]
    def findAll(): List[PriceAdjustment]
    def execute(id: Long, dto: PriceAdjustmentExecuteDto): Unit
    def addArticles(orderId: Long, articleIds: Set[Long])
    def deleteArticle(orderId: Long, articleIds: Set[Long])
}

@Transactional
@Component
class PriceAdjustmentServiceImpl extends PriceAdjustmentService {
    private var priceAdjustmentRepository: PriceAdjustmentRepository= _
    @Autowired
    def setPriceAdjustmentRepository(PriceAdjustmentRepository: PriceAdjustmentRepository) {
        this.priceAdjustmentRepository = PriceAdjustmentRepository
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

    def createNew(): PriceAdjustment = {
        val order = new PriceAdjustment
        this.priceAdjustmentRepository.save(order)
        order
    }

    @Transactional(readOnly = true)
    def articles(id: Long): Set[Article] = {
        this.priceAdjustmentRepository.findById(id).get.listArticles
    }

    @Transactional(readOnly = true)
    def findAll(): List[PriceAdjustment] = {
        this.priceAdjustmentRepository.findAll()
    }

    def execute(id: Long, dto: PriceAdjustmentExecuteDto) {
        val order = this.priceAdjustmentRepository.findById(id).get
        order.effectiveDate = dto.effectiveDate
        order.remark = dto.remark
        order.listArticles.foreach {article =>
            this.articleRepository.update(article)
        }
        this.priceAdjustmentRepository.update(order)
    }

    def addArticles(orderId: Long, articleIds: Set[Long]) {
        val order = this.priceAdjustmentRepository.findById(orderId).get
        articleIds.map(this.articleRepository.findById(_).get).foreach(order.addArticle(_))
        this.priceAdjustmentRepository.update(order)
    }

    def deleteArticle(orderId: Long, articleIds: Set[Long]) {
        val order = this.priceAdjustmentRepository.findById(orderId).get
        articleIds.map(this.articleRepository.findById(_).get).foreach(order.removeArticle(_))
        this.priceAdjustmentRepository.update(order)
    }
}
