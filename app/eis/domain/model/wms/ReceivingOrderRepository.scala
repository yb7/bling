package eis.domain.model.wms

/**
 * User: abin
 * Date: 13-9-12
 * Time: 下午6:32
 */
trait ReceivingOrderRepository {
    def findAll(): List[ReceivingOrder]
    def findById(id: Long): Option[ReceivingOrder]
    def save(order: ReceivingOrder): Long
    def update(order: ReceivingOrder): Unit
    def delete(id: Long): Unit
}
