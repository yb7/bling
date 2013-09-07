package eis.domain.model.foundation

/**
 * User: abin
 * Date: 13-9-7
 * Time: 上午10:56
 */
trait CompanyRepository {
    def findAll(): List[Company]
    def findById(id: Long): Option[Company]
    def save(company: Company): Long
    def update(company: Company): Unit
}
