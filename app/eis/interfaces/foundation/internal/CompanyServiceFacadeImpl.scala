package eis.interfaces.foundation.internal

import eis.application.foundation.CompanyService
import eis.interfaces.foundation.facade.{CompanyCreateDto, CompanyDto, CompanyServiceFacade}
import eis.interfaces.foundation.internal.assembler.CompanyDtoAssembler
import eis.domain.model.foundation.Company

/**
 * User: abin
 * Date: 13-9-7
 * Time: 下午1:14
 */
class CompanyServiceFacadeImpl(companyService: CompanyService) extends CompanyServiceFacade {
    def query(): List[CompanyDto] = {
        companyService.query().map(CompanyDtoAssembler.toDto(_))
    }

    def create(dto: CompanyCreateDto): CompanyDto = {
        val domain = new Company
        domain.name = dto.name
        domain.shortCode = dto.shortCode
        companyService.createNew(domain)
        CompanyDtoAssembler.toDto(domain)
    }
}
