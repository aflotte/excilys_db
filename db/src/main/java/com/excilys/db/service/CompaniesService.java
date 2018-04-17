package com.excilys.db.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.exception.ValidatorException;
import com.excilys.db.model.Company;
import com.excilys.db.persistance.CompaniesDAO;
import com.excilys.db.validator.CompaniesValidator;

@Service("companiesService")
@EnableTransactionManagement
public class CompaniesService implements ICompaniesService {
    @Autowired
    private CompaniesDAO companies;
    @Autowired
    private CompaniesValidator companiesValidator;

    /* (non-Javadoc)
     * @see com.excilys.db.service.ICompaniesService#listCompanies()
     */
    @Override
    public List<Company> listCompanies() {
        return companies.listCompanies();
    }

    /* (non-Javadoc)
     * @see com.excilys.db.service.ICompaniesService#getCompanies(int)
     */
    @Override
    public Optional<Company> getCompanies(int id) throws CompaniesInexistantException {
        return companies.getCompany(id);
    }

    /* (non-Javadoc)
     * @see com.excilys.db.service.ICompaniesService#getCompagnyId(java.lang.String)
     */
    @Override
    public Integer getCompagnyId(String name) {
        if (!companies.getIdFromName(name).isEmpty()) {
            return companies.getIdFromName(name).get(0);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void destroy(int id) throws ValidatorException {
        companiesValidator.exist(id);
        companies.deleteCompany(id);
    }
}