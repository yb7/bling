package eis.application.wms

import eis.domain.model.wms.{WarehouseRepository, PriceAdjustment, PriceAdjustmentRepository}
import eis.domain.model.article.{ArticleRepository, Article}
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import controllers.eis.wms.PriceAdjustmentExecuteDto
import org.quartz.Scheduler

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
    private var scheduler: Scheduler = _
    @Autowired
    def setScheduler(scheduler: Scheduler) {
        this.scheduler = scheduler
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

        assembleFromDto(order, dto)

        scheduleJob(order)

        this.priceAdjustmentRepository.update(order)
    }


    private def scheduleJob(order: PriceAdjustment) {
        import org.quartz.TriggerBuilder._

        val trigger = newTrigger().withIdentity(order.bizCode, "PriceAdjustment")
                .forJob("PriceAdjustment", "Sys")
                .withDescription(s"调价${order.bizCode}")
                .usingJobData("PriceAdjustmentId", order.id.toString)
                .startAt(order.effectiveDate.toDate)
                .build()


        if (scheduler.checkExists(trigger.getKey)) {
            scheduler.unscheduleJob(trigger.getKey)

        }
        scheduler.scheduleJob(trigger)
    }

    private def assembleFromDto(order: PriceAdjustment, dto: PriceAdjustmentExecuteDto) {
        order.effectiveDate = dto.effectiveDate
        order.remark = dto.remark

        order.retailPriceDirectly = dto.retailPriceDirectly
        order.incrementBaseOnCost = dto.incrementBaseOnCost
        order.coefficientBaseOnCost = dto.coefficientBaseOnCost

        order.incrementBaseOnRetailPrice = dto.incrementBaseOnRetailPrice
        order.coefficientBaseOnRetailPrice = dto.coefficientBaseOnRetailPrice

        order.excludeUnits0 = dto.excludeUnits0
        order.excludeUnits1 = dto.excludeUnits1
        order.excludeUnits2 = dto.excludeUnits2
        order.excludeUnits3 = dto.excludeUnits3
        order.excludeUnits4 = dto.excludeUnits4
        order.excludeUnits5 = dto.excludeUnits5
        order.excludeUnits6 = dto.excludeUnits6
        order.excludeUnits7 = dto.excludeUnits7
        order.excludeUnits8 = dto.excludeUnits8
        order.excludeUnits9 = dto.excludeUnits9
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
