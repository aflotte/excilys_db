package com.excilys.db.test;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.db.dao.CompaniesDAO;
import com.excilys.db.model.Company;
import com.excilys.db.persistance.DBConnection;

import junit.framework.TestCase;

public class CompaniesDAOTest extends TestCase {
	DBConnection instance = DBConnection.getInstance();
	CompaniesDAO companies = CompaniesDAO.INSTANCE;	

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
		Company Apple = new Company();
		Apple.setId(1);
		Apple.setName("Apple Inc.");
		assertEquals(Apple,companies.listCompanies().get(0));
	}
}
