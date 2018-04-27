package com.excilys.db.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.db.config.TestConfig;
import com.excilys.db.exception.DAOConfigurationException;
import com.excilys.db.model.Company;
import com.excilys.db.persistance.ICompaniesDAO;
import com.excilys.db.persistance.IComputerDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CompaniesDAOTest {
    @Autowired
    ICompaniesDAO companiesDAO;
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
    
    @Test
    public void testGetCount() {
        assertEquals(6,companiesDAO.getCount());
    }
    
    @Test
    public void testGetIdForName() {
        List<Integer> list = companiesDAO.getIdFromName("Apple Inc.");
        assertEquals(1,list.get(0).intValue());
    }
    
    @Test
    public void testExistCompanyTrue() {
        assertTrue(companiesDAO.existCompanies(1));
    }
    
    @Test
    public void testExistCompanyFalse() {
        assertFalse(companiesDAO.existCompanies(15));
    }
    
    @Test
    public void testListCompany() {
        Company company = new Company();
        company.setId(3);
        company.setName("RCA");
        assertEquals(company,companiesDAO.listCompanies().get(2));
    }
    
    @Test
    public void testGetCompanyNull() {
        assertFalse(companiesDAO.getCompany(15869).isPresent());
    }
    
    @Test
    public void testComputerFromCompany() {
        List<Integer> computers = new ArrayList<>(Arrays.asList(2,3,4,5,14,15));
        assertEquals(computers, companiesDAO.computerFromCompany(2));
    }
}