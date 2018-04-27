package com.excilys.db.validator;


import java.time.LocalDate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.db.exception.CompaniesIdIncorrectException;
import com.excilys.db.exception.IncoherentDatesException;
import com.excilys.db.exception.ValidatorException;
import com.excilys.db.model.Computer;
import com.excilys.db.persistance.ICompaniesDAO;
import com.excilys.db.persistance.IComputerDAO;

@Component
public class ComputerValidator {
    @Autowired
    private CompaniesValidator companiesValidator;
    @Autowired
    private IComputerDAO computer;
    @Autowired 
    private ICompaniesDAO company;
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerValidator.class);

    /**
     *
     * @param id dont on test l'existance
     * @return l'existance
     */
    public boolean exist(int id) {
        return computer.showDetails(id).isPresent();
    }

    /**
     *
     * @param computer test la cohérence des dates
     * @return si les dates sont cohérentes ( false = dates cohérentes )
     */
    public boolean testDate(Computer computer) {
        LocalDate introduced = computer.getIntroduced();
        LocalDate discontinued = computer.getDiscontinued();
        return ((introduced != null) && (discontinued != null) && (introduced.compareTo(discontinued) > 0));
    }

    /**
     *
     * @param computer dont on test l'id compagnie
     * @return si l'id de la compagnie est correct
     */
    public boolean testIdCompany(Computer computer) {
        if (computer.getCompany().getId() == null) {
            return true;
        }
        return company.existCompanies(computer.getCompany().getId());
    }

    /**
     *
     * @param computer computer to validate
     * @return if the computer is valid
     * @throws IncoherentDatesException error with dates
     * @throws CompaniesIdIncorrectException error with companies
     * @throws ValidatorException exception levé par le validateur
     * @throws ComputerNameStrangeException 
     */
    public boolean validate(Computer computer) throws IncoherentDatesException, CompaniesIdIncorrectException {
        if (testDate(computer)) {
            throw new IncoherentDatesException();
        }
        if (!companiesValidator.check(computer.getCompany())) {
            throw new CompaniesIdIncorrectException();
        }
        return true;
    }
}
