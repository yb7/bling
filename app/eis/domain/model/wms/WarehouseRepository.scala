package eis.domain.model.wms

/**
 * User: abin
 * Date: 13-9-7
 * Time: 上午10:56
 */
trait WarehouseRepository {
    def findAll(): List[Warehouse]
    def findById(id: Long): Option[Warehouse]
    def save(company: Warehouse): Long
    def update(company: Warehouse): Unit
    def delete(id: Long): Unit
}
