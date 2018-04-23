package com.excilys.db.validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import com.excilys.db.exception.CompaniesIdIncorrectException;
import com.excilys.db.exception.ComputerNameStrangeException;
import com.excilys.db.exception.IncoherentDatesException;
import com.excilys.db.exception.ValidatorException;
import com.excilys.db.model.Computer;

@Component
public class ComputerValidator {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private CompaniesValidator companiesValidator;
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerValidator.class);

    /**
     *
     * @param id dont on test l'existance
     * @return l'existance
     */
    public boolean exist(int id) {
        String querry = "SELECT name FROM computer WHERE id = ?";
        try (Connection conn = DataSourceUtils.getConnection(dataSource); PreparedStatement prep1 = conn.prepareStatement(querry);) {
            prep1.setInt(1, id);
            try (ResultSet resultSet = prep1.executeQuery();) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return false;
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
        String querry = "SELECT name FROM company WHERE id = " + computer.getCompany().getId();
        try (Connection conn = DataSourceUtils.getConnection(dataSource); PreparedStatement prep1 = conn.prepareStatement(querry);) {
            try (ResultSet resultSet = prep1.executeQuery();) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return false;
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
    public boolean validate(Computer computer) throws IncoherentDatesException, CompaniesIdIncorrectException, ValidatorException {
        if (testDate(computer)) {
            throw new IncoherentDatesException();
        }
        if (!companiesValidator.check(computer.getCompany())) {
            throw new CompaniesIdIncorrectException();
        }
        return true;
    }
}
