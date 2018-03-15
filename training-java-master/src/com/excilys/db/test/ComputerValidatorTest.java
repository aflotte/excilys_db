package com.excilys.db.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.excilys.db.DAO.ComputerDAO;
import com.excilys.db.moddel.Computer;
import com.excilys.db.persistance.DB_Connection;

import junit.framework.TestCase;

public class ComputerValidatorTest extends TestCase {
	DB_Connection instance = DB_Connection.getInstance();
	ComputerDAO computer = ComputerDAO.getInstance();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
	
	@Test
	public void testExist() throws ParseException {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Date date = formatter.parse("1999/12/5");
		Test.setIntroduced(date);
		Test.setDiscontinued(date);
		Test.setCompanyId(1);
		int id = computer.createAComputer(Test);
	}
	
}
