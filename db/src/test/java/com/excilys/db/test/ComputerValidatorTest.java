package com.excilys.db.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.db.exception.CompaniesIdIncorrectException;
import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.exception.IncoherentDatesException;
import com.excilys.db.model.Company;
import com.excilys.db.model.Computer;
import com.excilys.db.persistance.IComputerDAO;
import com.excilys.db.validator.ComputerValidator;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"/applicationContext.xml"})
public class ComputerValidatorTest extends TestCase {

	@Autowired
	IComputerDAO computer;
    @Autowired
    private ComputerValidator computerValidator;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

	@Test
	public void testExist() throws ParseException, IncoherentDatesException, CompaniesIdIncorrectException, CompaniesInexistantException {
		Computer Test = new Computer();
		Test.setName("Test_Computer");
		Date date = formatter.parse("1999/12/5");
		Test.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		Test.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		Test.setCompany(new Company(1));
		int id = computer.createAComputer(Test);
		assertEquals(true, computerValidator.exist(id));
		List<Integer> testList2 = computer.getIdFromName("Test_Computer");	
		for (int i = 0; i < testList2.size(); i++) {
			computer.deleteAComputer(testList2.get(i));
		}
		assertEquals(false, computerValidator.exist(id));
	}

}
