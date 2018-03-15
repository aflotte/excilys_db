package com.excilys.db.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.db.DAO.ComputerDAO;
import com.excilys.db.moddel.Computer;
import com.excilys.db.persistance.DB_Connection;

import junit.framework.TestCase;

public class ComputerTest extends TestCase {

	static DB_Connection instance;
	static ComputerDAO computer;
	static ResultSet toUseForTest;
	static Connection conn;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
	
	@BeforeClass
	public void init() {
		instance = DB_Connection.getInstance();
		String querry = "SELECT name, introduced, discontinued, company_id FROM computer";
		try {
			PreparedStatement prep1 = conn.prepareStatement(querry);
			toUseForTest = prep1.executeQuery();
			toUseForTest.next();
		} catch (SQLException e) {
			e.printStackTrace();
			DB_Connection.getInstance().disconnect();
		}
	}
	
	@Before
	public void connection() {
		instance.connect();
		conn = DB_Connection.getConn();
	}
	
	@After
	public void deconnection() {
		instance.disconnect();
	}
	
	
	
	
	
	//Test de non régression :
	@Test
	public void testToString() {
		Computer comp = new Computer();
		comp.setName("Name");
		comp.setIntroduced(null);
		comp.setDiscontinued(null);
		comp.setCompanyId(10);
		assertEquals(comp.toString()," | name=Name | introduced=null | discontinued=null | companyId=10");
	}
	
	@Test
	public void testEqualsTrue() {
		Computer comp = new Computer();
		comp.setName("Name");
		comp.setIntroduced(null);
		comp.setDiscontinued(null);
		comp.setCompanyId(10);
		Computer comp2 = new Computer();
		comp2.setName("Name");
		comp2.setIntroduced(null);
		comp2.setDiscontinued(null);
		comp2.setCompanyId(10);
		assertEquals(true,comp.equals(comp2));
	}
	
	@Test
	public void testEqualsSame() {
		Computer comp = new Computer();
		comp.setName("Name");
		comp.setIntroduced(null);
		comp.setDiscontinued(null);
		comp.setCompanyId(10);
		assertEquals(true,comp.equals(comp));
	}
	
	@Test
	public void testEqualsFalseClass() {
		Computer comp = new Computer();
		comp.setName("Name");
		comp.setIntroduced(null);
		comp.setDiscontinued(null);
		comp.setCompanyId(10);
		Integer comp2 = new Integer(5);
		assertEquals(false,comp.equals(comp2));
	}
	
	@Test
	public void testEqualsFalseName() {
		Computer comp = new Computer();
		comp.setName("Name");
		comp.setIntroduced(null);
		comp.setDiscontinued(null);
		comp.setCompanyId(10);
		Computer comp2 = new Computer();
		comp2.setName("Name2");
		comp2.setIntroduced(null);
		comp2.setDiscontinued(null);
		comp2.setCompanyId(10);
		assertEquals(false,comp.equals(comp2));
	}
	
	@Test
	public void testEqualsFalseIntroduced() throws ParseException {
		Computer comp = new Computer();
		comp.setName("Name");
		comp.setIntroduced(null);
		comp.setDiscontinued(null);
		comp.setCompanyId(10);
		Computer comp2 = new Computer();
		comp2.setName("Name2");
		Date date = formatter.parse("1999/12/5");
		comp2.setIntroduced(date);
		comp2.setDiscontinued(null);
		comp2.setCompanyId(10);
		assertEquals(false,comp.equals(comp2));
	}
	
	@Test
	public void testEqualsFalseDiscontinued() throws ParseException {
		Computer comp = new Computer();
		comp.setName("Name");
		comp.setIntroduced(null);
		comp.setDiscontinued(null);
		comp.setCompanyId(10);
		Computer comp2 = new Computer();
		comp2.setName("Name2");
		Date date = formatter.parse("1999/12/5");
		comp2.setIntroduced(null);
		comp2.setDiscontinued(date);
		comp2.setCompanyId(10);
		assertEquals(false,comp.equals(comp2));
	}
	
	@Test
	public void testEqualsFalseCompanyId(){
		Computer comp = new Computer();
		comp.setName("Name");
		comp.setIntroduced(null);
		comp.setDiscontinued(null);
		comp.setCompanyId(10);
		Computer comp2 = new Computer();
		comp2.setName("Name2");
		comp2.setIntroduced(null);
		comp2.setDiscontinued(null);
		comp2.setCompanyId(11);
		assertEquals(false,comp.equals(comp2));
	}
	
}
