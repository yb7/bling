package eis.interfaces.foundation.internal.assembler

import eis.domain.model.foundation.Company
import eis.interfaces.foundation.facade.CompanyDto

/**
 * User: abin
 * Date: 13-9-7
 * Time: 下午1:16
 */
object CompanyDtoAssembler {
    def toDto(company: Company) = {
        CompanyDto(company.id, company.shortCode, company.name)
    }

}
