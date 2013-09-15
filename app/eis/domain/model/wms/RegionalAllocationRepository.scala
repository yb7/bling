package eis.domain.model.wms

/**
 * User: abin
 * Date: 13-9-15
 * Time: 上午9:11
 */
trait RegionalAllocationRepository {
    def findAll(): List[RegionalAllocation]
    def findById(id: Long): Option[RegionalAllocation]
    def save(order: RegionalAllocation): Long
    def update(order: RegionalAllocation): Unit
    def delete(id: Long): Unit
}
