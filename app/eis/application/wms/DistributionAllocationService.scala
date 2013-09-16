package eis.application.wms

import eis.domain.model.wms.{WarehouseRepository, DistributionAllocation, DistributionAllocationRepository}
import eis.domain.model.article.{ArticleRepository, Article}
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import controllers.eis.wms.DistributionAllocationExecuteDto

/**
 * User: abin
 * Date: 13-9-15
 * Time: 上午8:15
 */
trait DistributionAllocationService {
    def createNew(): DistributionAllocation
    def articles(id: Long): Set[Article]
    def findAll(): List[DistributionAllocation]
    def execute(id: Long, dto: DistributionAllocationExecuteDto): Unit
    def addArticles(orderId: Long, articleIds: Set[Long])
    def deleteArticle(orderId: Long, articleIds: Set[Long])
}

@Transactional
@Component
class DistributionAllocationServiceImpl extends DistributionAllocationService {
    private var DistributionAllocationRepository: DistributionAllocationRepository= _
    @Autowired
    def setDistributionAllocationRepository(DistributionAllocationRepository: DistributionAllocationRepository) {
        this.DistributionAllocationRepository = DistributionAllocationRepository
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

    def createNew(): DistributionAllocation = {
        val order = new DistributionAllocation
        this.DistributionAllocationRepository.save(order)
        order
    }

    @Transactional(readOnly = true)
    def articles(id: Long): Set[Article] = {
        this.DistributionAllocationRepository.findById(id).get.listArticles
    }

    @Transactional(readOnly = true)
    def findAll(): List[DistributionAllocation] = {
        this.DistributionAllocationRepository.findAll()
    }

    def execute(id: Long, dto: DistributionAllocationExecuteDto) {
        val order = this.DistributionAllocationRepository.findById(id).get
        order.distributionDate = dto.distributionDate
//        order.sendWarehouse = warehouseRepository.findById(dto.sendWarehouseId).get
        order.receiveWarehouse = warehouseRepository.findById(dto.receiveWarehouseId).get
        order.remark = dto.remark
        order.listArticles.foreach {article =>
            article.warehouse = order.receiveWarehouse
            this.articleRepository.update(article)
        }
        this.DistributionAllocationRepository.update(order)
    }

    def addArticles(orderId: Long, articleIds: Set[Long]) {
        val order = this.DistributionAllocationRepository.findById(orderId).get
        articleIds.map(this.articleRepository.findById(_).get).foreach(order.addArticle(_))
        this.DistributionAllocationRepository.update(order)
    }

    def deleteArticle(orderId: Long, articleIds: Set[Long]) {
        val order = this.DistributionAllocationRepository.findById(orderId).get
        articleIds.map(this.articleRepository.findById(_).get).foreach(order.removeArticle(_))
        this.DistributionAllocationRepository.update(order)
    }
}
