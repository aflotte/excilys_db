package com.excilys.db.test;

//import java.sql.Connection;

import org.junit.Test;

import com.excilys.db.dao.ComputerDAO;
import com.excilys.db.persistance.DB_Connection;

import junit.framework.TestCase;

public class ComputerMapperTest extends TestCase {

	DB_Connection instance = DB_Connection.getInstance();
	ComputerDAO computer = ComputerDAO.getInstance();
	//	private static Connection conn;
	//TODO:
	@Test
	public void testMapper() {
		assertEquals(true,true);
	}


}
