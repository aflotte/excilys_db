package com.excilys.db.validator;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.excilys.db.exception.ValidatorException;
import com.excilys.db.model.Company;
import com.excilys.db.model.Computer;

@Component
public class CompaniesValidator {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompaniesValidator.class);
    static Computer computer;
    @Autowired
    private DataSource dataSource;

    /**
     *
     * @param id dont on test l'existance
     * @return l'existance
     * @throws ValidatorException une exception du validateur
     */
    public boolean exist(int id) {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        String querry = "SELECT name FROM company WHERE id = %d";
        return vJdbcTemplate.queryForObject(String.format(querry,id),String.class).isEmpty();
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
            String querry = "SELECT name FROM company WHERE id = " + company.getId();
            JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
            return vJdbcTemplate.queryForObject(querry,String.class).isEmpty();
        }
    }
}
