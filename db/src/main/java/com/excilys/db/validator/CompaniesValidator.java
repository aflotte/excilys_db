package com.excilys.db.validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
     */
    private void init() {
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
        String querry = "SELECT name FROM company WHERE id = " + id;
        try {
            PreparedStatement prep1 = conn.prepareStatement(querry);
            resultSet = prep1.executeQuery();
            return (resultSet.next());
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            DBConnection.getInstance().disconnect();
        }
        return false;
    }

    /**
     *
     * @param company la compagnie a tester
     * @return si elle est correct
     */
    public boolean check(Company company) {
        init();
        ResultSet resultSet = null;
        if (company.getId() == null) {
            company.getName().equals("");
        }
        String querry = "SELECT name FROM company WHERE id = " + company.getId();
        try {
            PreparedStatement prep1 = conn.prepareStatement(querry);
            resultSet = prep1.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1).equals(company.getName());
            } else {
                return false;
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            DBConnection.getInstance().disconnect();
        }
        return false;
    }
}
