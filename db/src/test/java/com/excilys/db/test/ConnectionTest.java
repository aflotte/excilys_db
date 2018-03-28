package com.excilys.db.test;


import java.sql.Connection;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.excilys.db.persistance.DBConnection;

import junit.framework.TestCase;

public class ConnectionTest extends TestCase{

	@InjectMocks private DBConnection dbConnection;

	@Mock private Connection mockConnection;

	@Mock private Statement mockStatement;



	@Before

	public void setUp() {

		MockitoAnnotations.initMocks(this);

	}



	@Test

	public void testMockDBConnection() throws Exception {

		
	}
}
