package eis.application.wms

import eis.domain.model.wms.{WarehouseRepository, RegionalAllocation, RegionalAllocationRepository}
import eis.domain.model.article.{ArticleRepository, Article}
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import controllers.eis.wms.RegionalAllocationExecuteDto

/**
 * User: abin
 * Date: 13-9-15
 * Time: 上午8:15
 */
trait RegionalAllocationService {
    def createNew(): RegionalAllocation
    def articles(id: Long): Set[Article]
    def findAll(): List[RegionalAllocation]
    def execute(id: Long, dto: RegionalAllocationExecuteDto): Unit
    def addArticles(orderId: Long, articleIds: Set[Long])
    def deleteArticle(orderId: Long, articleIds: Set[Long])
}

@Transactional
@Component
class RegionalAllocationServiceImpl extends RegionalAllocationService {
    private var regionalAllocationRepository: RegionalAllocationRepository= _
    @Autowired
    def setRegionalAllocationRepository(regionalAllocationRepository: RegionalAllocationRepository) {
        this.regionalAllocationRepository = regionalAllocationRepository
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

    def createNew(): RegionalAllocation = {
        val order = new RegionalAllocation
        this.regionalAllocationRepository.save(order)
        order
    }

    @Transactional(readOnly = true)
    def articles(id: Long): Set[Article] = {
        this.regionalAllocationRepository.findById(id).get.listArticles
    }

    @Transactional(readOnly = true)
    def findAll(): List[RegionalAllocation] = {
        this.regionalAllocationRepository.findAll()
    }

    def execute(id: Long, dto: RegionalAllocationExecuteDto) {
        val order = this.regionalAllocationRepository.findById(id).get
        order.allocationDate = dto.allocationDate
//        order.sendWarehouse = warehouseRepository.findById(dto.sendWarehouseId).get
        order.receiveWarehouse = warehouseRepository.findById(dto.receiveWarehouseId).get
        order.remark = dto.remark
        order.listArticles.foreach {article =>
            article.warehouse = order.receiveWarehouse
            this.articleRepository.update(article)
        }
        this.regionalAllocationRepository.update(order)
    }

    def addArticles(orderId: Long, articleIds: Set[Long]) {
        val order = this.regionalAllocationRepository.findById(orderId).get
        articleIds.map(this.articleRepository.findById(_).get).foreach(order.addArticle(_))
        this.regionalAllocationRepository.update(order)
    }

    def deleteArticle(orderId: Long, articleIds: Set[Long]) {
        val order = this.regionalAllocationRepository.findById(orderId).get
        articleIds.map(this.articleRepository.findById(_).get).foreach(order.removeArticle(_))
        this.regionalAllocationRepository.update(order)
    }
}
