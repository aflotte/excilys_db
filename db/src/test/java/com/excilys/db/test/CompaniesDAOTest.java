package com.excilys.db.test;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.db.DAO.CompaniesDAO;
import com.excilys.db.model.Companies;
import com.excilys.db.persistance.DB_Connection;

import junit.framework.TestCase;

public class CompaniesDAOTest extends TestCase {
	DB_Connection instance = DB_Connection.getInstance();
	CompaniesDAO companies = CompaniesDAO.getInstance();	
	
	@BeforeClass
	public void init() {
	}
	
	@Before
	public void connection() {
		instance.connect();
	}
	
	@After
	public void deconnection() {
		instance.disconnect();
	}
	
	
	//Uniquement avec la base initiale ( McBook en 1 )
	@Test
	public void testListCompanies() {
		Companies Apple = new Companies();
		Apple.setName("Apple Inc.");
		assertEquals(Apple,companies.listCompanies().get(0));
	}
}
