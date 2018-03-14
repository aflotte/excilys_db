package com.excilys.db.test;

import com.excilys.db.DAO.ComputerDAO;
import com.excilys.db.mapper.Computer;
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
		computer = ComputerDAO.getInstance();
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
	public void testListComputer() {
		computer = ComputerDAO.getInstance();
		Computer McBook = new Computer();
		McBook.setName("MacBook Pro 15.4 inch");
		McBook.setIntroduced(null);
		McBook.setDiscontinued(null);
		McBook.setCompanyId(1);
		assertEquals(new Integer(1),ComputerDAO.getInstance().getId(McBook).get(0));
	}

	
	
}
