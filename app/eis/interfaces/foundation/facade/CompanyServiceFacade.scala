package eis.interfaces.foundation.facade

import eis.application.foundation.CompanyService

/**
 * User: abin
 * Date: 13-9-7
 * Time: 下午1:06
 */

trait CompanyServiceFacade {
    def query(): List[CompanyDto]

    def create(company: CompanyCreateDto): CompanyDto
}
case class CompanyDto(id: Long, shortCode: String, name: String)

case class CompanyCreateDto(shortCode: String, name: String)

