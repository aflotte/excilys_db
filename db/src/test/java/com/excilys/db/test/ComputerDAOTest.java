package com.excilys.db.test;

import com.excilys.db.dao.CompaniesDAO;
import com.excilys.db.dao.ComputerDAO;
import com.excilys.db.exception.CompaniesIdIncorrectException;
import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.exception.IncoherentDatesException;
import com.excilys.db.model.Company;
import com.excilys.db.model.Computer;
import com.excilys.db.persistance.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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

	DBConnection instance = DBConnection.getInstance();
	ComputerDAO computer = ComputerDAO.INSTANCE;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

	@After
	public void deconnection() {
		List<Integer> testList2 = computer.getIdFromName("Test_Computer");	
		for (int i = 0; i < testList2.size(); i++) {
			computer.deleteAComputer(testList2.get(i));
		}
		instance.disconnect();
	}

	/**
	 * Database initialization for testing i.e.
	 * 
	 * @throws SQLException
	 */
	private static void initDatabase() throws SQLException {
		try (Connection connection = getConnection();
				Statement statement = connection.createStatement();) {
			statement.execute("create table company (id bigint not null auto_increment,name varchar(255), constraint pk_company primary key (id));");
			statement.execute("create table computer (id bigint not null auto_increment, name varchar(255),introduced timestamp NULL,discontinued timestamp NULL,"
+" company_id bigint default NULL,constraint pk_computer primary key (id));");
			statement.execute("alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;");
			connection.commit();
			statement.executeUpdate(
					"insert into company (id,name) values (  1,'Apple Inc.');");
			statement.executeUpdate("insert into computer (id,name,introduced,discontinued,company_id) values (  1,'MacBook Pro 15.4 inch',null,null,1);");
			statement.executeUpdate("insert into computer (id,name,introduced,discontinued,company_id) values ( 16,'Apple II','1977-04-01','1993-10-01',1);");
			connection.commit();
		}
	}

	/**
	 * Create a connection
	 * 
	 * @return connection object
	 * @throws SQLException
	 */
	private static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:hsqldb:mem:testjcdb", "sa", "");
	}




	protected void tearDown() throws Exception {
		super.tearDown();
		List<Integer> testList2 = computer.getIdFromName("Test_Computer");	
		for (int i = 0; i < testList2.size(); i++) {
			computer.deleteAComputer(testList2.get(i));
		}
		instance.disconnect();
	}

	//Uniquement avec la base initiale ( McBook en 1 )
	@Test
	public void testListComputer() throws CompaniesInexistantException, IncoherentDatesException, CompaniesIdIncorrectException {
		Computer McBook = new Computer();
		McBook.setName("MacBook Pro 15.4 inch");
		McBook.setIntroduced(null);
		McBook.setDiscontinued(null);
		Company companie = new Company(1);
		companie.setName("Apple Inc.");
		McBook.setCompany(companie);
		assertEquals(McBook,computer.listComputer().get(0));
	}

	@Test
	public void testShowDetails() throws CompaniesInexistantException, IncoherentDatesException, CompaniesIdIncorrectException {
		Computer McBook = new Computer();
		McBook.setName("MacBook Pro 15.4 inch");
		McBook.setIntroduced(null);
		McBook.setDiscontinued(null);
		Company companie = new Company(1);
		companie.setName("Apple Inc.");
		McBook.setCompany(companie);
		assertEquals(McBook,computer.showDetails(1).get());
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
	public void testCreateAComputerDatesNull() throws CompaniesInexistantException, IncoherentDatesException, CompaniesIdIncorrectException {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Test.setIntroduced(null);
		Test.setDiscontinued(null);
		Company companie = new Company(1);
		companie.setName("Apple Inc.");
		Test.setCompany(companie);
		computer.createAComputer(Test);
	}

	@Test
	public void testCreateAComputerDateIntroNull() throws ParseException, CompaniesInexistantException, IncoherentDatesException, CompaniesIdIncorrectException {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Test.setIntroduced(null);
		Date date = formatter.parse("1999/12/5");
		Test.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		Company companie = new Company(1);
		companie.setName("Apple Inc.");
		Test.setCompany(companie);
		computer.createAComputer(Test);
	}

	@Test
	public void testCreateAComputerDateDiscNull() throws ParseException, CompaniesInexistantException, IncoherentDatesException, CompaniesIdIncorrectException {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Date date = formatter.parse("1999/12/5");
		Test.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		Test.setDiscontinued(null);
		Company companie = new Company(1);
		companie.setName("Apple Inc.");
		Test.setCompany(companie);
		computer.createAComputer(Test);
	}

	@Test
	public void testCreateAComputerDateNotNull() throws ParseException, CompaniesInexistantException, IncoherentDatesException, CompaniesIdIncorrectException {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Date date = formatter.parse("1999/12/5");
		Test.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		Test.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		Company companie = new Company(1);
		companie.setName("Apple Inc.");
		Test.setCompany(companie);
		computer.createAComputer(Test);
	}

	@Test
	public void testUpdateAComputerDatesNullCompanieNull() throws IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Test.setIntroduced(null);
		Test.setDiscontinued(null);
		Company companie = new Company();
		Test.setCompany(companie);
		computer.createAComputer(Test);
		int id = computer.getId(Test).get(0);
		computer.updateAComputer(Test, id);
	}

	@Test
	public void testUpdateAComputerDatesNull() throws CompaniesInexistantException, IncoherentDatesException, CompaniesIdIncorrectException {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Test.setIntroduced(null);
		Test.setDiscontinued(null);
		Test.setCompany(CompaniesDAO.INSTANCE.getCompany(1).get());
		computer.createAComputer(Test);
		int id = computer.getId(Test).get(0);
		computer.updateAComputer(Test, id);
	}

	@Test
	public void testUpdateAComputerDateIntroNull() throws ParseException, CompaniesInexistantException, IncoherentDatesException, CompaniesIdIncorrectException {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Test.setIntroduced(null);
		Date date = formatter.parse("1999/12/5");
		Test.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		Company companie = new Company(2);
		companie.setName("Apple Inc.");
		Test.setCompany(companie);
		computer.createAComputer(Test);
		int id = computer.getId(Test).get(0);
		computer.updateAComputer(Test, id);
	}

	@Test
	public void testUpdateAComputerDateDiscNull() throws ParseException, CompaniesInexistantException, IncoherentDatesException, CompaniesIdIncorrectException {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Date date = formatter.parse("1999/12/5");
		Test.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		Test.setDiscontinued(null);
		Company companie = new Company(1);
		companie.setName("Apple Inc.");
		Test.setCompany(companie);
		computer.createAComputer(Test);
		int id = computer.getId(Test).get(0);
		computer.updateAComputer(Test, id);
	}

	@Test
	public void testUpdateAComputerDateNotNull() throws ParseException, CompaniesInexistantException, IncoherentDatesException, CompaniesIdIncorrectException {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Date date = formatter.parse("1999/12/5");
		Test.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		Test.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		Company companie = new Company(1);
		companie.setName("Apple Inc.");
		Test.setCompany(companie);
		computer.createAComputer(Test);
		int id = computer.getId(Test).get(0);
		computer.updateAComputer(Test, id);
	}

	//Uniquement avec la base initiale ( McBook en 1 )
	@Test
	public void testGetIdComputer() throws CompaniesInexistantException, IncoherentDatesException, CompaniesIdIncorrectException {
		Computer McBook = new Computer();
		McBook.setName("MacBook Pro 15.4 inch");
		McBook.setIntroduced(null);
		McBook.setDiscontinued(null);
		Company companie = new Company(1);
		companie.setName("Apple Inc.");
		McBook.setCompany(companie);
		assertEquals(new Integer(1),computer.getId(McBook).get(0));
	}

	//Uniquement avec la base initiale ( McBook en 1 )
	@Test
	public void testGetIdFromName() {
		assertEquals(new Integer(1),computer.getIdFromName("MacBook Pro 15.4 inch").get(0));
	}

	@Test
	public void testDelete() throws IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
		Computer Test = new Computer();
		Test.setName("Test_Destruction");
		Test.setIntroduced(null);
		Test.setDiscontinued(null);
		computer.createAComputer(Test);
		List<Integer> testList = computer.getIdFromName("Test_Destruction");	
		computer.deleteAComputer(testList.get(0));
	}
	
}
