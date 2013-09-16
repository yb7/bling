package eis.domain.model.wms

/**
 * User: abin
 * Date: 13-9-15
 * Time: 上午9:11
 */
trait DistributionAllocationRepository {
    def findAll(): List[DistributionAllocation]
    def findById(id: Long): Option[DistributionAllocation]
    def save(order: DistributionAllocation): Long
    def update(order: DistributionAllocation): Unit
    def delete(id: Long): Unit
}
