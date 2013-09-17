package eis.domain.service.wms

import eis.domain.model.wms.PriceAdjustmentRepository
import org.springframework.stereotype.Service
import util.JobInTransaction
import org.quartz.JobExecutionContext
import org.springframework.beans.factory.annotation.Autowired
import eis.domain.model.article.{ArticleRepository, Article}
import org.slf4j.LoggerFactory

/**
 * User: abin
 * Date: 13-9-17
 * Time: 上午9:51
 */
trait PriceAdjustmentExecutor {
    def execute(priceAdjustmentId: Long)
}

@Service
class PriceAdjustmentExecutorImpl @Autowired()(priceAdjustmentRepository: PriceAdjustmentRepository, articleRepository: ArticleRepository)
        extends PriceAdjustmentExecutor {
    private val logger = LoggerFactory.getLogger(this.getClass)

    def execute(priceAdjustmentId: Long) {
        logger.debug(s"execute adjust for ${priceAdjustmentId}")
        val adjustment = priceAdjustmentRepository.findById(priceAdjustmentId).get

        val retailPriceTactics:(Article => BigDecimal) = {article =>
            if (adjustment.retailPriceDirectly > 0) {
                adjustment.retailPriceDirectly
            } else if (adjustment.incrementBaseOnCost > 0 || adjustment.coefficientBaseOnCost > 0) {
                (article.totalProcurementSettlement + adjustment.incrementBaseOnCost) * (1 + adjustment.coefficientBaseOnCost)
            } else if (adjustment.incrementBaseOnRetailPrice > 0) {
                article.retailPrice + adjustment.incrementBaseOnRetailPrice
            } else {
                article.retailPrice * (1 + adjustment.coefficientBaseOnRetailPrice)
            }
        }

        adjustment.listArticles.foreach {article =>
            article.retailPrice = retailPriceTactics(article)
            articleRepository.update(article)
        }
        logger.debug(s"execute adjust for ${priceAdjustmentId} finished!!")
    }
}

class PriceAdjustmentJob extends JobInTransaction {
    lazy val priceAdjustmentExecutor = getBean(classOf[PriceAdjustmentExecutor])
    def executeInTransaction(context: JobExecutionContext) {
        val id = context.getMergedJobDataMap.get("PriceAdjustmentId").asInstanceOf[String].toLong
        priceAdjustmentExecutor.execute(id)
    }
}