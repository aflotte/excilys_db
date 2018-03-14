package com.excilys.db.test;


import org.junit.Test;
import com.excilys.db.persistance.DB_Connection;

import junit.framework.TestCase;

public class ConnectionTest extends TestCase{

	@Test
	public void test() {
		DB_Connection testConnection = DB_Connection.getInstance();
		testConnection.connect();
		testConnection.disconnect();
	}

}
