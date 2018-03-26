package com.excilys.db.validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.excilys.db.exception.CompaniesIdIncorrectException;
import com.excilys.db.exception.IncoherentDatesException;
import com.excilys.db.model.Computer;
import com.excilys.db.persistance.DBConnection;

public enum ComputerValidator {
    INSTANCE;
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerValidator.class);
    private static Connection conn;


/**
 *
 */
    private static void init() {
        DBConnection.getInstance().connect();
        conn = DBConnection.getConn();

    }

/**
 *
 * @param id dont on test l'existance
 * @return l'existance
 */
    public boolean exist(int id) {
        init();
        ResultSet resultSet = null;
        String querry = "SELECT name FROM computer WHERE id = " + id;
        try {
            PreparedStatement prep1 = conn.prepareStatement(querry);
            resultSet = prep1.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            DBConnection.getInstance().disconnect();
        }
        DBConnection.getInstance().disconnect();
        return false;
    }

    /**
     *
     * @param computer test la cohérence des dates
     * @return si les dates sont cohérentes
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
    public static boolean testIdCompany(Computer computer) {
        init();
        ResultSet resultSet = null;
        if (computer.getCompany().getId() == null) {
            return true;
        }
        String querry = "SELECT name FROM company WHERE id = " + computer.getCompany().getId();
        try {
            PreparedStatement prep1 = conn.prepareStatement(querry);
            resultSet = prep1.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            DBConnection.getInstance().disconnect();
        }
        DBConnection.getInstance().disconnect();
        return false;
    }

    /**
     *
     * @param computer computer to validate
     * @return if the computer is valid
     * @throws IncoherentDatesException error with dates
     * @throws CompaniesIdIncorrectException error with companies
     */
    public boolean validate(Computer computer) throws IncoherentDatesException, CompaniesIdIncorrectException {
        if (testDate(computer)) {
            throw new IncoherentDatesException();
        }
        if (!CompaniesValidator.INSTANCE.check(computer.getCompany())) {
            throw new CompaniesIdIncorrectException();
        }
        return true;
    }
}
