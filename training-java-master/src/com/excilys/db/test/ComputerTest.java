package com.excilys.db.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.db.DAO.ComputerDAO;
import com.excilys.db.mapper.Computer;
import com.excilys.db.persistance.DB_Connection;

import junit.framework.TestCase;

public class ComputerTest extends TestCase {

	//la base computer ne doit pas être vide pour que le test fonctionne
	static DB_Connection instance;
	static ComputerDAO computer;
	static ResultSet toUseForTest;
	static Connection conn;
	
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
	
	@Test
	public void testResultToComputer() {
		try {
			toUseForTest.next();
			Computer comp = Computer.ResultToComputer(toUseForTest);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testToString() {
		Computer comp = new Computer();
		comp.setName("Name");
		comp.setIntroduced(null);
		comp.setDiscontinued(null);
		comp.setCompanyId(10);
		assertEquals(comp.toString()," name=Name introduced=null discontinued=null companyId=10");
	}
}
