package eis.domain.model.wms

/**
 * User: abin
 * Date: 13-9-15
 * Time: 上午9:11
 */
trait OutwardProcessingRepository {
    def findAll(): List[OutwardProcessing]
    def findById(id: Long): Option[OutwardProcessing]
    def save(order: OutwardProcessing): Long
    def update(order: OutwardProcessing): Unit
    def delete(id: Long): Unit
}
