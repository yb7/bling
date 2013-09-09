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
    def findById(id: Long): Option[Company]
    def update(company: Company): Unit
    def delete(id: Long): Unit
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
    @Transactional(readOnly = true)
    def query(): List[Company] = {
        companyRepository.findAll()
    }
    @Transactional(readOnly = true)
    def findById(id: Long): Option[Company] = {
        companyRepository.findById(id)
    }

    def update(company: Company) {
        companyRepository.update(company)
    }

    def delete(id: Long) {
        companyRepository.delete(id)
    }
}
