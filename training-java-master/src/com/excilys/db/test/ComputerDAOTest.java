package com.excilys.db.test;

import com.excilys.db.DAO.ComputerDAO;
import com.excilys.db.persistance.DB_Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;

public class ComputerDAOTest extends TestCase {

	DB_Connection instance;
	ComputerDAO computer;
	
	@BeforeClass
	public void init() {
		instance = DB_Connection.getInstance();
	}
	
	@Before
	public void connection() {
		instance.connect();
	}
	
	@After
	public void deconnection() {
		instance.disconnect();
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

	
	
}
