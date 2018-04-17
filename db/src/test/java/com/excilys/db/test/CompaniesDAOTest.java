package com.excilys.db.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.db.exception.DAOConfigurationException;
import com.excilys.db.model.Company;
import com.excilys.db.persistance.CompaniesDAO;
import com.excilys.db.persistance.IComputerDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"/applicationContext.xml"})
public class CompaniesDAOTest {
    @Autowired
    CompaniesDAO companiesDAO;
    @Autowired
    private DataSource dataSource;
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompaniesDAOTest.class);
    @Before
    public void init() throws SQLException, IOException, ClassNotFoundException, DAOConfigurationException, SqlToolError {
        logger.debug("début init");
        try (Connection connection = dataSource.getConnection();
                java.sql.Statement statement = connection.createStatement();
                InputStream inputStream = DataSource.class.getResourceAsStream("/TEST.sql"); ) {
            SqlFile sqlFile = new SqlFile(new InputStreamReader(inputStream), "init", System.out, "UTF-8", false,
                    new File("."));
            sqlFile.setConnection(connection);
            sqlFile.execute();
            logger.debug("fin init");
        } catch (SQLException e) {
            logger.debug("problem in init", e);
        }
    }

    @Autowired
    IComputerDAO computer;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

    @Test
    public void testListCompanies() {
        Company Apple = new Company();
        Apple.setId(1);
        Apple.setName("Apple Inc.");
        assertEquals(Apple,companiesDAO.listCompanies().get(0));}
}
