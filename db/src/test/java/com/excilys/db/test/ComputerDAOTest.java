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
    }

    protected void tearDown() {
        List<Integer> testList2 = computer.getIdFromName("Test_Computer");	
        for (int i = 0; i < testList2.size(); i++) {
            computer.deleteAComputer(testList2.get(i));
        }
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
    //TODO: corriger
    /*
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
*/
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
