package eis.domain.model.foundation

/**
 * User: abin
 * Date: 13-9-12
 * Time: 下午6:09
 */
trait BizCodeRepository {
    def nextBizCode(prefix: BizCodePrefix.Value): String
}

/**
 * 业务编号的前缀必须为全字母的。如果不是，那么BizCodeUtil.getPrefix方法就会错误
 */
object BizCodePrefix extends Enumeration {
    val ReceivingOrder = Value("REC")
    val RegionalAllocation = Value("REA")
    val Article = Value("ART")
    val DistributionAllocation = Value("DSA")
    val OutwardProcessing = Value("OUT")
    val PriceAdjustment = Value("PAD")
}