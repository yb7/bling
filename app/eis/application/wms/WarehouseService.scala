package eis.application.wms

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import eis.domain.model.wms.{WarehouseRepository, Warehouse}

/**
 * User: abin
 * Date: 13-9-7
 * Time: 上午11:31
 */


trait WarehouseService {
    def createNew(warehouse: Warehouse)
    def query(): List[Warehouse]
    def findById(id: Long): Option[Warehouse]
    def update(warehouse: Warehouse): Unit
    def delete(id: Long): Unit
}

@Transactional
@Component
class WarehouseServiceImpl extends WarehouseService {

    private var warehouseRepository: WarehouseRepository = _

    @Autowired
    def setWarehouseRepository(repo: WarehouseRepository) {
        warehouseRepository = repo
    }

    def createNew(warehouse: Warehouse) {
        warehouseRepository.save(warehouse)
    }
    @Transactional(readOnly = true)
    def query(): List[Warehouse] = {
        warehouseRepository.findAll()
    }
    @Transactional(readOnly = true)
    def findById(id: Long): Option[Warehouse] = {
        warehouseRepository.findById(id)
    }

    def update(warehouse: Warehouse) {
        warehouseRepository.update(warehouse)
    }

    def delete(id: Long) {
        warehouseRepository.delete(id)
    }
}
