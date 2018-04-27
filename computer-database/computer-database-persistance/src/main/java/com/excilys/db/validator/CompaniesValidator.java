package com.excilys.db.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.db.exception.ValidatorException;
import com.excilys.db.model.Company;
import com.excilys.db.model.Computer;
import com.excilys.db.persistance.ICompaniesDAO;

@Component
public class CompaniesValidator {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompaniesValidator.class);
    static Computer computer;
    @Autowired 
    private ICompaniesDAO company;

    /**
     *
     * @param id dont on test l'existance
     * @return l'existance
     * @throws ValidatorException une exception du validateur
     */
    public boolean exist(int id) {
        return company.existCompanies(id);
    }

    /**
     *
     * @param company la compagnie a tester
     * @return si elle est correct
     * @throws ValidatorException une exception du validateur
     */
    public boolean check(Company company) {
        if (company.getId() == null) {
            company.setName("");
            return true;
        } else {
            return this.company.existCompanies(company.getId());
        }
    }
}
