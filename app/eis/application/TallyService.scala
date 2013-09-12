package eis.application

import eis.domain.model.article.Article
import eis.domain.model.wms.Warehouse

/**
 * User: abin
 * Date: 13-9-7
 * Time: 上午10:12
 */
trait TallyService {
    def enteringWarehouse(warehouse: Warehouse, articles: List[Article])
}


