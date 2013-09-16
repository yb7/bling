package eis.domain.model.wms

/**
 * User: abin
 * Date: 13-9-15
 * Time: 上午9:11
 */
trait PriceAdjustmentRepository {
    def findAll(): List[PriceAdjustment]
    def findById(id: Long): Option[PriceAdjustment]
    def save(order: PriceAdjustment): Long
    def update(order: PriceAdjustment): Unit
    def delete(id: Long): Unit
}
