package com.excilys.db.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

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
import com.excilys.db.exception.CompaniesIdIncorrectException;
import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.exception.DAOConfigurationException;
import com.excilys.db.exception.IncoherentDatesException;
import com.excilys.db.model.Company;
import com.excilys.db.model.Computer;
import com.excilys.db.persistance.IComputerDAO;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ComputerTest extends TestCase {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerTest.class);
    @Autowired
    IComputerDAO computer;
    ResultSet toUseForTest = null;
    @Autowired
    private DataSource dataSource;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

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

    @Test
    public void testToString() throws IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
        Computer comp = new Computer();
        comp.setName("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        Company companie = new Company(10);
        companie.setName("Digital Equipment Corporation");
        comp.setCompany(companie);
        assertEquals(" | name=Name | introduced=null | discontinued=null | companyId=Digital Equipment Corporation",comp.toString());
    }
    
    @Test
    public void testToStringCompanyNull() throws IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
        Computer comp = new Computer();
        comp.setName("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        Company companie = null;
        comp.setCompany(companie);
        assertEquals(" | name=Name | introduced=null | discontinued=null | companyId=null",comp.toString());
    }

    @Test
    public void testEqualsTrue() throws IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
        Computer comp = new Computer();
        comp.setName("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        Computer comp2 = new Computer();
        comp2.setName("Name");
        comp2.setIntroduced(null);
        comp2.setDiscontinued(null);
        comp2.setCompany(new Company(10));
        assertEquals(true,comp.equals(comp2));
        assertTrue(comp.hashCode() == comp2.hashCode());
    }

    @Test
    public void testEqualsSame() throws IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
        Computer comp = new Computer();
        comp.setName("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        assertEquals(true,comp.equals(comp));
        assertTrue(comp.hashCode() == comp.hashCode());
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void testEqualsFalseClass() throws IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
        Computer comp = new Computer();
        comp.setName("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        Integer comp2 = new Integer(5);
        assertEquals(false,comp.equals(comp2));
        assertTrue(comp.hashCode() != comp2.hashCode());
    }

    @Test
    public void testEqualsFalseName() throws IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
        Computer comp = new Computer();
        comp.setName("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        Computer comp2 = new Computer();
        comp2.setName("Name2");
        comp2.setIntroduced(null);
        comp2.setDiscontinued(null);
        comp2.setCompany(new Company(10));
        assertEquals(false,comp.equals(comp2));
        assertTrue(comp.hashCode() != comp2.hashCode());
    }

    @Test
    public void testEqualsFalseIntroduced() throws ParseException, IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
        Computer comp = new Computer();
        comp.setName("Name");
        Date date = formatter.parse("1999/12/6");
        comp.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        Computer comp2 = new Computer();
        comp2.setName("Name");
        Date date2 = formatter.parse("1999/12/5");
        comp2.setIntroduced(date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comp2.setDiscontinued(null);
        comp2.setCompany(new Company(10));
        assertEquals(false,comp.equals(comp2));
        assertTrue(comp.hashCode() != comp2.hashCode());
    }
    
    @Test
    public void testEqualsFalseDiscontinued() throws ParseException, IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
        Computer comp = new Computer();
        comp.setName("Name");
        Date date = formatter.parse("1999/12/6");
        comp.setIntroduced(null);
        comp.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comp.setCompany(new Company(10));
        Computer comp2 = new Computer();
        comp2.setName("Name");
        Date date2 = formatter.parse("1999/12/5");
        comp2.setIntroduced(null);
        comp2.setDiscontinued(date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comp2.setCompany(new Company(10));
        assertEquals(false,comp.equals(comp2));
        assertTrue(comp.hashCode() != comp2.hashCode());
    }
    
    @Test
    public void testEqualsTrueIntroduced() throws ParseException, IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
        Computer comp = new Computer();
        comp.setName("Name");
        Date date = formatter.parse("1999/12/5");
        comp.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        Computer comp2 = new Computer();
        comp2.setName("Name");
        comp2.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comp2.setDiscontinued(null);
        comp2.setCompany(new Company(10));
        assertEquals(true,comp.equals(comp2));
        assertTrue(comp.hashCode() == comp2.hashCode());
    }
    
    
    @Test
    public void testEqualsTrueDiscontinued() throws ParseException, IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
        Computer comp = new Computer();
        comp.setName("Name");
        Date date = formatter.parse("1999/12/5");
        comp.setIntroduced(null);
        comp.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comp.setCompany(new Company(10));
        Computer comp2 = new Computer();
        comp2.setName("Name");
        comp2.setIntroduced(null);
        comp2.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comp2.setCompany(new Company(10));
        assertEquals(true,comp.equals(comp2));
        assertTrue(comp.hashCode() == comp2.hashCode());
    }

    @Test
    public void testEqualsFalseNullIntroduced() throws ParseException, IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
        Computer comp = new Computer();
        comp.setName("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        Computer comp2 = new Computer();
        comp2.setName("Name");
        Date date = formatter.parse("1999/12/5");
        comp2.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comp2.setDiscontinued(null);
        comp2.setCompany(new Company(10));
        assertEquals(false,comp.equals(comp2));
        assertTrue(comp.hashCode() != comp2.hashCode());
    }
    
    
    @Test
    public void testEqualsFalseNullDiscontinued() throws ParseException, IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
        Computer comp = new Computer();
        comp.setName("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        Computer comp2 = new Computer();
        comp2.setName("Name");
        Date date = formatter.parse("1999/12/5");
        comp2.setIntroduced(null);
        comp2.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comp2.setCompany(new Company(10));
        assertEquals(false,comp.equals(comp2));
        assertTrue(comp.hashCode() != comp2.hashCode());
    }
    @Test
    public void testEqualsTrueNullDiscontinued() throws ParseException, IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
        Computer comp = new Computer();
        comp.setName("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        Computer comp2 = new Computer();
        comp2.setName("Name");
        comp2.setIntroduced(null);
        comp2.setDiscontinued(null);
        comp2.setCompany(new Company(10));
        assertEquals(true,comp.equals(comp2));
        assertTrue(comp.hashCode() == comp2.hashCode());
    }

    @Test
    public void testEqualsFalseCompanyId() throws IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException{
        Computer comp = new Computer();
        comp.setName("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        Computer comp2 = new Computer();
        comp2.setName("Name2");
        comp2.setIntroduced(null);
        comp2.setDiscontinued(null);
        comp2.setCompany(new Company(11));
        assertEquals(false,comp.equals(comp2));
        assertTrue(comp.hashCode() != comp2.hashCode());
    }
    
    @Test
    public void testEqualsTrueNullCompanyId() throws IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException{
        Computer comp = new Computer();
        comp.setName("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        comp.setCompany(null);
        Computer comp2 = new Computer();
        comp2.setName("Name2");
        comp2.setIntroduced(null);
        comp2.setDiscontinued(null);
        comp2.setCompany(null);
        assertEquals(false,comp.equals(comp2));
        assertTrue(comp.hashCode() != comp2.hashCode());
    }
    
    @Test
    public void testEqualsNull() throws IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException{
        Computer comp = new Computer();
        comp.setName("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        Computer comp2 = null;
        assertEquals(false,comp.equals(comp2));
    }
    

}
