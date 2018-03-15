package com.excilys.db.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.excilys.db.DAO.ComputerDAO;
import com.excilys.db.mapper.ComputerMapper;
import com.excilys.db.moddel.Computer;
import com.excilys.db.persistance.DB_Connection;
import com.excilys.db.service.ComputerService;

import junit.framework.TestCase;

public class ComputerMapperTest extends TestCase {

	DB_Connection instance = DB_Connection.getInstance();
	ComputerDAO computer = ComputerDAO.getInstance();
	private static Connection conn;


	
	
}
