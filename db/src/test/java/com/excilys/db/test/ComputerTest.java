package com.excilys.db.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.db.exception.CompaniesIdIncorrectException;
import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.exception.IncoherentDatesException;
import com.excilys.db.model.Company;
import com.excilys.db.model.Computer;
import com.excilys.db.persistance.ComputerDAO;
import com.excilys.db.persistance.DBConnection;

import junit.framework.TestCase;

public class ComputerTest extends TestCase {

	static DBConnection instance;
	static ComputerDAO computer;
	static ResultSet toUseForTest;
	static Connection conn;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

	@BeforeClass
	public void init() {
		String querry = "SELECT name, introduced, discontinued, company_id FROM computer";
		try {
			PreparedStatement prep1 = conn.prepareStatement(querry);
			toUseForTest = prep1.executeQuery();
			toUseForTest.next();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
                conn.close();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
		}
	}

    @Before
    public void connection() {
        conn = DBConnection.getConn();
    }

    @After
    public void deconnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }





	//Test de non régression :
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
	}

	@Test
	public void testEqualsSame() throws IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
		Computer comp = new Computer();
		comp.setName("Name");
		comp.setIntroduced(null);
		comp.setDiscontinued(null);
		comp.setCompany(new Company(10));
		assertEquals(true,comp.equals(comp));
	}

	@Test
	public void testEqualsFalseClass() throws IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
		Computer comp = new Computer();
		comp.setName("Name");
		comp.setIntroduced(null);
		comp.setDiscontinued(null);
		comp.setCompany(new Company(10));
		Integer comp2 = new Integer(5);
		assertEquals(false,comp.equals(comp2));
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
	}

	@Test
	public void testEqualsFalseIntroduced() throws ParseException, IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
		Computer comp = new Computer();
		comp.setName("Name");
		comp.setIntroduced(null);
		comp.setDiscontinued(null);
		comp.setCompany(new Company(10));
		Computer comp2 = new Computer();
		comp2.setName("Name2");
		Date date = formatter.parse("1999/12/5");
		comp2.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		comp2.setDiscontinued(null);
		comp2.setCompany(new Company(10));
		assertEquals(false,comp.equals(comp2));
	}

	@Test
	public void testEqualsFalseDiscontinued() throws ParseException, IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
		Computer comp = new Computer();
		comp.setName("Name");
		comp.setIntroduced(null);
		comp.setDiscontinued(null);
		comp.setCompany(new Company(10));
		Computer comp2 = new Computer();
		comp2.setName("Name2");
		Date date = formatter.parse("1999/12/5");
		comp2.setIntroduced(null);
		comp2.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		comp2.setCompany(new Company(10));
		assertEquals(false,comp.equals(comp2));
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
	}

}
