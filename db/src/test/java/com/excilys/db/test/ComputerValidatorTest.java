package com.excilys.db.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Test;

import com.excilys.db.DAO.ComputerDAO;
import com.excilys.db.exception.CompaniesIdIncorrect;
import com.excilys.db.exception.CompaniesInexistant;
import com.excilys.db.exception.IncoherentDates;
import com.excilys.db.model.Companies;
import com.excilys.db.model.Computer;
import com.excilys.db.persistance.DB_Connection;

import junit.framework.TestCase;

public class ComputerValidatorTest extends TestCase {
	DB_Connection instance = DB_Connection.getInstance();
	ComputerDAO computer = ComputerDAO.getInstance();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
	
	@Test
	public void testExist() throws ParseException, IncoherentDates, CompaniesIdIncorrect, CompaniesInexistant {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Date date = formatter.parse("1999/12/5");
		Test.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		Test.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		Test.setCompanyId(new Companies(1));
		int id = computer.createAComputer(Test);
	}
	
}
