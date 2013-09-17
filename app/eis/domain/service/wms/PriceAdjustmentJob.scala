package eis.domain.service.wms

import eis.domain.model.wms.PriceAdjustmentRepository
import org.springframework.stereotype.Service
import util.{JobInSpringContext, JobInTransaction}
import org.quartz.JobExecutionContext
import org.springframework.beans.factory.annotation.Autowired
import eis.domain.model.article.{ArticleRepository, Article}
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional

/**
 * User: abin
 * Date: 13-9-17
 * Time: 上午9:51
 */
trait PriceAdjustmentExecutor {
    def execute(priceAdjustmentId: Long)
}

@Transactional
@Service
class PriceAdjustmentExecutorImpl extends PriceAdjustmentExecutor {
    private val logger = LoggerFactory.getLogger(this.getClass)

    private var priceAdjustmentRepository: PriceAdjustmentRepository = _
    @Autowired
    def setPriceAdjustmentRepository(priceAdjustmentRepository: PriceAdjustmentRepository) {
        this.priceAdjustmentRepository = priceAdjustmentRepository
    }
    private var articleRepository: ArticleRepository = _
    @Autowired
    def setArticleRepository(articleRepository: ArticleRepository) {
        this.articleRepository = articleRepository
    }

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

class PriceAdjustmentJob extends JobInSpringContext {
    lazy val priceAdjustmentExecutor = getBean(classOf[PriceAdjustmentExecutor])
    def execute(context: JobExecutionContext) {
        val id = context.getMergedJobDataMap.get("PriceAdjustmentId").asInstanceOf[String].toLong
        priceAdjustmentExecutor.execute(id)
    }
}