package controllers.eis.foundation.assembler

import eis.domain.model.foundation.Company
import controllers.eis.foundation.CompanyDto

/**
 * User: abin
 * Date: 13-9-9
 * Time: 上午9:05
 */
object CompanyDtoAssembler {
  def toDto(company: Company) = {
    CompanyDto(company.id, company.shortCode, company.name, company.version)
  }

}

