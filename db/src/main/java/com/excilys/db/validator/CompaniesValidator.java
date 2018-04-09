package com.excilys.db.validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.db.exception.ValidatorException;
import com.excilys.db.model.Company;
import com.excilys.db.model.Computer;
import com.excilys.db.persistance.DBConnection;

public enum CompaniesValidator {
    INSTANCE;
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompaniesValidator.class);
    private static Connection conn;


    static Computer computer;


    /**
     *
     * @param id dont on test l'existance
     * @return l'existance
     * @throws ValidatorException 
     */
    public boolean exist(int id) throws ValidatorException {
        String querry = "SELECT name FROM company WHERE id = " + id;
        try (Connection conn = DBConnection.getConn();PreparedStatement prep1 = conn.prepareStatement(querry);){
            try (ResultSet resultSet = prep1.executeQuery();){
                return (resultSet.next());
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            throw new ValidatorException();
        }
    }

    /**
     *
     * @param company la compagnie a tester
     * @return si elle est correct
     * @throws ValidatorException 
     */
    public boolean check(Company company) throws ValidatorException {
        if (company.getId() == null) {
            company.setName("");
            return true;
        }else {
            String querry = "SELECT name FROM company WHERE id = " + company.getId();
            try (Connection conn = DBConnection.getConn();PreparedStatement prep1 = conn.prepareStatement(querry);){
                try (ResultSet resultSet = prep1.executeQuery();){
                    if (resultSet.next()) {
                        return resultSet.getString(1).equals(company.getName());
                    } else {
                        return false;
                    }
                }
            } catch (SQLException e) {
                logger.warn(e.getMessage());
                throw new ValidatorException();
            }
        }

    }
}
