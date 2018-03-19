package com.excilys.db.test;


import java.sql.Connection;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.db.persistance.DB_Connection;

import junit.framework.TestCase;

public class ConnectionTest extends TestCase{

	@InjectMocks private DB_Connection dbConnection;

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
