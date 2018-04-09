package com.excilys.db.test;

import java.sql.Connection;
import java.sql.SQLException;

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
	Connection conn;

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


	//Uniquement avec la base initiale ( McBook en 1 )
	@Test
	public void testListCompanies() {
		Company Apple = new Company();
		Apple.setId(1);
		Apple.setName("Apple Inc.");
		assertEquals(Apple,companies.listCompanies().get(0));
	}
}
