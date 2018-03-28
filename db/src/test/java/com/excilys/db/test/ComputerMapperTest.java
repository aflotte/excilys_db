package com.excilys.db.test;

//import java.sql.Connection;

import org.junit.Test;

import com.excilys.db.dao.ComputerDAO;
import com.excilys.db.persistance.DBConnection;

import junit.framework.TestCase;

public class ComputerMapperTest extends TestCase {

	DBConnection instance = DBConnection.getInstance();
	ComputerDAO computer = ComputerDAO.INSTANCE;
	//	private static Connection conn;
	//TODO:
	@Test
	public void testMapper() {
		assertEquals(true,true);
	}


}
