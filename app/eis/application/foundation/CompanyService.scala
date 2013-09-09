package eis.application.foundation

import eis.domain.model.foundation.{CompanyRepository, Company}
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

/**
 * User: abin
 * Date: 13-9-7
 * Time: 上午11:31
 */


trait CompanyService {
    def createNew(company: Company)
    def query(): List[Company]
}

@Transactional
@Component
class CompanyServiceImpl extends CompanyService {

    private var companyRepository: CompanyRepository = _

    @Autowired
    def setCompanyRepository(repo: CompanyRepository) {
        companyRepository = repo
    }

    def createNew(company: Company) {
        companyRepository.save(company)
    }
    def query(): List[Company] = {
        companyRepository.findAll()
    }
}
