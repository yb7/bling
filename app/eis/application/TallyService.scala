package eis.application

import eis.domain.model.warehouse.Warehouse
import eis.domain.model.article.Article

/**
 * User: abin
 * Date: 13-9-7
 * Time: 上午10:12
 */
trait TallyService {
    def enteringWarehouse(warehouse: Warehouse, articles: List[Article])
}


