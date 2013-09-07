package eis.application.foundation

import eis.domain.model.foundation.{CompanyRepository, Company}
import org.springframework.stereotype.Component

/**
 * User: abin
 * Date: 13-9-7
 * Time: 上午11:31
 */

trait CompanyService {
    def createNew(company: Company)
    def query(): List[Company]
}

@Component
class CompanyServiceImpl(companyRepository: CompanyRepository) extends CompanyService {
    def createNew(company: Company) {
        companyRepository.save(company)
    }

    def query(): List[Company] = {
        companyRepository.findAll()
    }
}
