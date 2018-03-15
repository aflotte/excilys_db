package com.excilys.db.test;

import com.excilys.db.DAO.CompaniesDAO;
import com.excilys.db.DAO.ComputerDAO;
import com.excilys.db.exception.CompaniesIdIncorrect;
import com.excilys.db.exception.CompaniesInexistant;
import com.excilys.db.exception.IncoherentDates;
import com.excilys.db.model.Computer;
import com.excilys.db.persistance.DB_Connection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;

public class ComputerDAOTest extends TestCase {

	DB_Connection instance = DB_Connection.getInstance();
	ComputerDAO computer = ComputerDAO.getInstance();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
	
	
	@BeforeClass
	public void init() {

	}
	
	@Before
	public void connection() {
		instance.connect();
	}
	
	@After
	public void deconnection() {
		List<Integer> testList2 = computer.getIdFromName("Test_Computer");	
    	for (int i = 0; i < testList2.size(); i++) {
    		computer.deleteAComputer(testList2.get(i));
    	}
		instance.disconnect();
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		List<Integer> testList2 = computer.getIdFromName("Test_Computer");	
    	for (int i = 0; i < testList2.size(); i++) {
    		computer.deleteAComputer(testList2.get(i));
    	}
		instance.disconnect();
	}
	
	@AfterClass
	public void delete() {
		
	}
	
	//Uniquement avec la base initiale ( McBook en 1 )
	@Test
	public void testListComputer() throws CompaniesInexistant, IncoherentDates, CompaniesIdIncorrect {
		Computer McBook = new Computer();
		McBook.setName("MacBook Pro 15.4 inch");
		McBook.setIntroduced(null);
		McBook.setDiscontinued(null);
		McBook.setCompanyId(1);
		assertEquals(McBook,computer.listComputer().get(0));
	}
	
	@Test
	public void testShowDetails() throws CompaniesInexistant, IncoherentDates, CompaniesIdIncorrect {
		Computer McBook = new Computer();
		McBook.setName("MacBook Pro 15.4 inch");
		McBook.setIntroduced(null);
		McBook.setDiscontinued(null);
		McBook.setCompanyId(1);
		assertEquals(McBook,computer.showDetails(1));
	}



	
	@Test
	public void testCreateAComputerDatesNullCompanieNull() {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Test.setIntroduced(null);
		Test.setDiscontinued(null);
		computer.createAComputer(Test);
	}
	
	@Test
	public void testCreateAComputerDatesNull() throws CompaniesInexistant, IncoherentDates, CompaniesIdIncorrect {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Test.setIntroduced(null);
		Test.setDiscontinued(null);
		Test.setCompanyId(1);
		computer.createAComputer(Test);
	}
	
	@Test
	public void testCreateAComputerDateIntroNull() throws ParseException, CompaniesInexistant, IncoherentDates, CompaniesIdIncorrect {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Test.setIntroduced(null);
		Date date = formatter.parse("1999/12/5");
		Test.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		Test.setCompanyId(1);
		computer.createAComputer(Test);
	}
	
	@Test
	public void testCreateAComputerDateDiscNull() throws ParseException, CompaniesInexistant, IncoherentDates, CompaniesIdIncorrect {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Date date = formatter.parse("1999/12/5");
		Test.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		Test.setDiscontinued(null);
		Test.setCompanyId(1);
		computer.createAComputer(Test);
	}
	
	@Test
	public void testCreateAComputerDateNotNull() throws ParseException, CompaniesInexistant, IncoherentDates, CompaniesIdIncorrect {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Date date = formatter.parse("1999/12/5");
		Test.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		Test.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		Test.setCompanyId(1);
		computer.createAComputer(Test);
	}
	
	@Test
	public void testUpdateAComputerDatesNullCompanieNull() {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Test.setIntroduced(null);
		Test.setDiscontinued(null);
		computer.createAComputer(Test);
		int id = computer.getId(Test).get(0);
		computer.updateAComputer(Test, id);
	}
	
	@Test
	public void testUpdateAComputerDatesNull() throws CompaniesInexistant, IncoherentDates, CompaniesIdIncorrect {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Test.setIntroduced(null);
		Test.setDiscontinued(null);
		Test.setCompanyId(CompaniesDAO.getInstance().getCompanies(1));
		computer.createAComputer(Test);
		int id = computer.getId(Test).get(0);
		computer.updateAComputer(Test, id);
	}
	
	@Test
	public void testUpdateAComputerDateIntroNull() throws ParseException, CompaniesInexistant, IncoherentDates, CompaniesIdIncorrect {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Test.setIntroduced(null);
		Date date = formatter.parse("1999/12/5");
		Test.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		Test.setCompanyId(2);
		System.out.println("computer = " + Test);
		computer.createAComputer(Test);
		int id = computer.getId(Test).get(0);
		computer.updateAComputer(Test, id);
	}
	
	@Test
	public void testUpdateAComputerDateDiscNull() throws ParseException, CompaniesInexistant, IncoherentDates, CompaniesIdIncorrect {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Date date = formatter.parse("1999/12/5");
		Test.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		Test.setDiscontinued(null);
		Test.setCompanyId(1);
		computer.createAComputer(Test);
		int id = computer.getId(Test).get(0);
		computer.updateAComputer(Test, id);
	}
	
	@Test
	public void testUpdateAComputerDateNotNull() throws ParseException, CompaniesInexistant, IncoherentDates, CompaniesIdIncorrect {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Date date = formatter.parse("1999/12/5");
		Test.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		Test.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		Test.setCompanyId(1);
		computer.createAComputer(Test);
		int id = computer.getId(Test).get(0);
		computer.updateAComputer(Test, id);
	}
	
	//Uniquement avec la base initiale ( McBook en 1 )
	@Test
	public void testGetIdComputer() throws CompaniesInexistant, IncoherentDates, CompaniesIdIncorrect {
		Computer McBook = new Computer();
		McBook.setName("MacBook Pro 15.4 inch");
		McBook.setIntroduced(null);
		McBook.setDiscontinued(null);
		McBook.setCompanyId(1);
		System.out.println(computer.getId(McBook).get(0));
		assertEquals(new Integer(1),computer.getId(McBook).get(0));
	}

	//Uniquement avec la base initiale ( McBook en 1 )
	@Test
	public void testGetIdFromName() {
		assertEquals(new Integer(1),computer.getIdFromName("MacBook Pro 15.4 inch").get(0));
	}
	
	@Test
	public void testDelete() {
		Computer Test = new Computer();
		Test.setName("Test_Destruction");
		Test.setIntroduced(null);
		Test.setDiscontinued(null);
		computer.createAComputer(Test);
		List<Integer> testList = computer.getIdFromName("Test_Destruction");	
		computer.deleteAComputer(testList.get(0));
	}
}
