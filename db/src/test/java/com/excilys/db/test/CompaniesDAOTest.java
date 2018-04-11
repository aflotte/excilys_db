package com.excilys.db.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.db.exception.DAOConfigurationException;
import com.excilys.db.model.Company;
import com.excilys.db.persistance.CompaniesDAO;
import com.excilys.db.persistance.ComputerDAO;
import com.excilys.db.persistance.DBConnection;

import junit.framework.TestCase;

public class CompaniesDAOTest {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompaniesDAOTest.class);
    @BeforeClass
    public static void init() throws SQLException, IOException, ClassNotFoundException, DAOConfigurationException, SqlToolError {
        logger.debug("début init");
        try (Connection connection = DBConnection.getConn();
                java.sql.Statement statement = connection.createStatement();
                InputStream inputStream = DBConnection.class.getResourceAsStream("/TEST.sql"); ) {
            SqlFile sqlFile = new SqlFile(new InputStreamReader(inputStream), "init", System.out, "UTF-8", false,
                    new File("."));
            sqlFile.setConnection(connection);
            sqlFile.execute();
            logger.debug("fin init");
        } catch (SQLException e) {
            logger.debug("problem in init", e);
        }
    }

    DBConnection instance = DBConnection.getInstance();
    ComputerDAO computer = ComputerDAO.INSTANCE;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

	@Test
	public void testListCompanies() {
		Company Apple = new Company();
		Apple.setId(1);
		Apple.setName("Apple Inc.");
		assertEquals(Apple,CompaniesDAO.INSTANCE.listCompanies().get(0));
	}
}
