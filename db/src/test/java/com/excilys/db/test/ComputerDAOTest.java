package com.excilys.db.test;

import com.excilys.db.config.TestConfig;
import com.excilys.db.exception.CompaniesIdIncorrectException;
import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.exception.DAOConfigurationException;
import com.excilys.db.exception.IncoherentDatesException;
import com.excilys.db.model.Company;
import com.excilys.db.model.Computer;
import com.excilys.db.persistance.ICompaniesDAO;
import com.excilys.db.persistance.IComputerDAO;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ComputerDAOTest {
    @Autowired
    ICompaniesDAO companiesDAO;
    @Autowired
    private DataSource dataSource;
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerDAOTest.class);
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


    protected void tearDown() {
        logger.debug("TearDown");
    }

    @Test
    public void testListComputer() throws CompaniesInexistantException, IncoherentDatesException, CompaniesIdIncorrectException {
        Computer McBook = new Computer("CM-200");
        McBook.setIntroduced(null);
        McBook.setDiscontinued(null);
        Company companie = new Company(2);
        companie.setName("Thinking Machines");
        McBook.setCompany(companie);
        assertEquals(McBook,computer.listComputer().get(2));
    }

    
    
    @Test
    public void testShowDetails() throws CompaniesInexistantException, IncoherentDatesException, CompaniesIdIncorrectException {
        Computer McBook = new Computer("CM-200");
        McBook.setIntroduced(null);
        McBook.setDiscontinued(null);
        Company companie = new Company(2);
        companie.setName("Thinking Machines");
        McBook.setCompany(companie);
        assertEquals(McBook,computer.showDetails(3).get());
    }


    @Test
    public void testShowDetailsEmpty() {
        computer.showDetails(169819);
    }

    @Test
    public void testCreateAComputerDatesNullCompanieNull() {
        Computer Test = new Computer("Test_Computer");
        Test.setIntroduced(null);
        Test.setDiscontinued(null);
        computer.createAComputer(Test);
    }

    @Test
    public void testCreateAComputerDatesNull() throws CompaniesInexistantException, IncoherentDatesException, CompaniesIdIncorrectException {
        Computer Test = new Computer("Test_Computer");
        Test.setIntroduced(null);
        Test.setDiscontinued(null);
        Company companie = new Company(1);
        companie.setName("Apple Inc.");
        Test.setCompany(companie);
        computer.createAComputer(Test);
    }

    @Test
    public void testCreateAComputerDateIntroNull() throws ParseException, CompaniesInexistantException, IncoherentDatesException, CompaniesIdIncorrectException {
        Computer Test = new Computer("Test_Computer");
        Test.setIntroduced(null);
        Date date = formatter.parse("1999/12/5");
        Test.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        Company companie = new Company(1);
        companie.setName("Apple Inc.");
        Test.setCompany(companie);
        computer.createAComputer(Test);
    }

    @Test
    public void testCreateAComputerDateDiscNull() throws ParseException, CompaniesInexistantException, IncoherentDatesException, CompaniesIdIncorrectException {
        Computer Test = new Computer("Test_Computer");
        Date date = formatter.parse("1999/12/5");
        Test.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        Test.setDiscontinued(null);
        Company companie = new Company(1);
        companie.setName("Apple Inc.");
        Test.setCompany(companie);
        computer.createAComputer(Test);
    }

    @Test
    public void testCreateAComputerDateNotNull() throws ParseException, CompaniesInexistantException, IncoherentDatesException, CompaniesIdIncorrectException {
        Computer Test = new Computer("Test_Computer");
        Date date = formatter.parse("1999/12/5");
        Test.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        Test.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        Company companie = new Company(1);
        companie.setName("Apple Inc.");
        Test.setCompany(companie);
        computer.createAComputer(Test);
    }

    @Test
    public void testUpdateAComputerDatesNullCompanieNull() throws IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
        Computer Test = new Computer("Test_Computer");
        Test.setIntroduced(null);
        Test.setDiscontinued(null);
        Company companie = new Company();
        Test.setCompany(companie);
        computer.createAComputer(Test);
        computer.updateAComputer(Test, 2);
    }

    @Test
    public void testUpdateAComputerDatesNull() throws CompaniesInexistantException, IncoherentDatesException, CompaniesIdIncorrectException {
        Computer Test = new Computer("Test_Computer");
        Test.setIntroduced(null);
        Test.setDiscontinued(null);
        Test.setCompany(companiesDAO.getCompany(1).get());
        computer.createAComputer(Test);
        computer.updateAComputer(Test, 2);
    }

    
    @Test
    public void testUpdateAComputerDateIntroNull() throws ParseException, CompaniesInexistantException, IncoherentDatesException, CompaniesIdIncorrectException {
        Computer Test = new Computer("Test_Computer");
        Test.setIntroduced(null);
        Date date = formatter.parse("1999/12/5");
        Test.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        Company companie = new Company(2);
        companie.setName("Apple Inc.");
        Test.setCompany(companie);
        computer.createAComputer(Test);
        computer.updateAComputer(Test, 2);
    }

    @Test
    public void testUpdateAComputerDateDiscNull() throws ParseException, CompaniesInexistantException, IncoherentDatesException, CompaniesIdIncorrectException {
        Computer Test = new Computer("Test_Computer");
        Date date = formatter.parse("1999/12/5");
        Test.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        Test.setDiscontinued(null);
        Company companie = new Company(1);
        companie.setName("Apple Inc.");
        Test.setCompany(companie);
        computer.createAComputer(Test);
        computer.updateAComputer(Test, 2);
    }

    @Test
    public void testUpdateAComputerDateNotNull() throws ParseException, CompaniesInexistantException, IncoherentDatesException, CompaniesIdIncorrectException {
        Computer Test = new Computer("Test_Computer");
        Date date = formatter.parse("1999/12/5");
        Test.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        Test.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        Company companie = new Company(1);
        companie.setName("Apple Inc.");
        Test.setCompany(companie);
        computer.createAComputer(Test);
        computer.updateAComputer(Test, 2);
    }

    @Test
    public void testGetIdFromName() {
        assertEquals(new Integer(3),computer.getIdFromName("CM-200").get(0));
    }

    @Test
    public void testDelete() throws IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
        Computer Test = new Computer("Test_Destruction");
        Test.setIntroduced(null);
        Test.setDiscontinued(null);
        computer.createAComputer(Test);
        List<Integer> testList = computer.getIdFromName("Test_Destruction");    
        computer.deleteAComputer(testList.get(0));
    }
    
    @Test
    public void testGetCount() {
        
    }

}
