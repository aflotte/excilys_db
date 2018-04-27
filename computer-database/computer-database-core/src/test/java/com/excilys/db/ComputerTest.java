package com.excilys.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

import com.excilys.db.model.Company;
import com.excilys.db.model.Computer;

import junit.framework.TestCase;

public class ComputerTest extends TestCase {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerTest.class);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

    public void testToString() {
        Computer comp = new Computer("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        Company companie = new Company(10);
        companie.setName("Digital Equipment Corporation");
        comp.setCompany(companie);
        assertEquals(" | id = null | name=Name | introduced=null | discontinued=null | companyId=Digital Equipment Corporation",comp.toString());
    }
    
    public void testToStringCompanyNull() {
        Computer comp = new Computer("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        Company companie = null;
        comp.setCompany(companie);
        assertEquals(" | id = null | name=Name | introduced=null | discontinued=null | companyId=null",comp.toString());
    }

    public void testEqualsTrue() {
        Computer comp = new Computer("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        Computer comp2 = new Computer("Name");
        comp2.setIntroduced(null);
        comp2.setDiscontinued(null);
        comp2.setCompany(new Company(10));
        assertEquals(true,comp.equals(comp2));
        assertTrue(comp.hashCode() == comp2.hashCode());
    }

    public void testEqualsSame() {
        Computer comp = new Computer("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        assertEquals(true,comp.equals(comp));
        assertTrue(comp.hashCode() == comp.hashCode());
    }

    @SuppressWarnings("unlikely-arg-type")
    public void testEqualsFalseClass() {
        Computer comp = new Computer("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        Integer comp2 = new Integer(5);
        assertEquals(false,comp.equals(comp2));
        assertTrue(comp.hashCode() != comp2.hashCode());
    }

    public void testEqualsFalseName() {
        Computer comp = new Computer("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        Computer comp2 = new Computer("Name2");
        comp2.setIntroduced(null);
        comp2.setDiscontinued(null);
        comp2.setCompany(new Company(10));
        assertEquals(false,comp.equals(comp2));
        assertTrue(comp.hashCode() != comp2.hashCode());
    }

    public void testEqualsFalseIntroduced() throws ParseException {
        Computer comp = new Computer("Name");
        Date date = formatter.parse("1999/12/6");
        comp.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        Computer comp2 = new Computer("Name");
        Date date2 = formatter.parse("1999/12/5");
        comp2.setIntroduced(date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comp2.setDiscontinued(null);
        comp2.setCompany(new Company(10));
        assertEquals(false,comp.equals(comp2));
        assertTrue(comp.hashCode() != comp2.hashCode());
    }

    public void testEqualsFalseDiscontinued() throws ParseException {
        Computer comp = new Computer("Name");
        Date date = formatter.parse("1999/12/6");
        comp.setIntroduced(null);
        comp.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comp.setCompany(new Company(10));
        Computer comp2 = new Computer("Name");
        Date date2 = formatter.parse("1999/12/5");
        comp2.setIntroduced(null);
        comp2.setDiscontinued(date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comp2.setCompany(new Company(10));
        assertEquals(false,comp.equals(comp2));
        assertTrue(comp.hashCode() != comp2.hashCode());
    }

    public void testEqualsTrueIntroduced() throws ParseException {
        Computer comp = new Computer("Name");
        Date date = formatter.parse("1999/12/5");
        comp.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        Computer comp2 = new Computer("Name");
        comp2.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comp2.setDiscontinued(null);
        comp2.setCompany(new Company(10));
        assertEquals(true,comp.equals(comp2));
        assertTrue(comp.hashCode() == comp2.hashCode());
    }

    public void testEqualsTrueDiscontinued() throws ParseException {
        Computer comp = new Computer("Name");
        Date date = formatter.parse("1999/12/5");
        comp.setIntroduced(null);
        comp.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comp.setCompany(new Company(10));
        Computer comp2 = new Computer("Name");
        comp2.setIntroduced(null);
        comp2.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comp2.setCompany(new Company(10));
        assertEquals(true,comp.equals(comp2));
        assertTrue(comp.hashCode() == comp2.hashCode());
    }

    public void testEqualsFalseNullIntroduced() throws ParseException {
        Computer comp = new Computer("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        Computer comp2 = new Computer("Name");
        Date date = formatter.parse("1999/12/5");
        comp2.setIntroduced(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comp2.setDiscontinued(null);
        comp2.setCompany(new Company(10));
        assertEquals(false,comp.equals(comp2));
        assertTrue(comp.hashCode() != comp2.hashCode());
    }

    public void testEqualsFalseNullDiscontinued() throws ParseException {
        Computer comp = new Computer("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        Computer comp2 = new Computer("Name");
        Date date = formatter.parse("1999/12/5");
        comp2.setIntroduced(null);
        comp2.setDiscontinued(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comp2.setCompany(new Company(10));
        assertEquals(false,comp.equals(comp2));
        assertTrue(comp.hashCode() != comp2.hashCode());
    }

    public void testEqualsTrueNullDiscontinued() throws ParseException {
        Computer comp = new Computer("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        Computer comp2 = new Computer("Name");
        comp2.setIntroduced(null);
        comp2.setDiscontinued(null);
        comp2.setCompany(new Company(10));
        assertEquals(true,comp.equals(comp2));
        assertTrue(comp.hashCode() == comp2.hashCode());
    }

    public void testEqualsFalseCompanyId() {
        Computer comp = new Computer("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        Computer comp2 = new Computer("Name");
        comp2.setIntroduced(null);
        comp2.setDiscontinued(null);
        comp2.setCompany(new Company(11));
        assertEquals(false,comp.equals(comp2));
        assertTrue(comp.hashCode() != comp2.hashCode());
    }

    public void testEqualsTrueNullCompanyId() {
        Computer comp = new Computer("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        comp.setCompany(null);
        Computer comp2 = new Computer("Name");
        comp2.setIntroduced(null);
        comp2.setDiscontinued(null);
        comp2.setCompany(null);
        assertEquals(true,comp.equals(comp2));
        assertTrue(comp.hashCode() == comp2.hashCode());
    }

    public void testEqualsNull() {
        Computer comp = new Computer("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        comp.setCompany(new Company(10));
        Computer comp2 = null;
        assertEquals(false,comp.equals(comp2));
    }

    public void testEqualsFalseCompanyNull() {
        Computer comp = new Computer("Name");
        comp.setIntroduced(null);
        comp.setDiscontinued(null);
        comp.setCompany(null);
        Computer comp2 = new Computer("Name");
        comp2.setIntroduced(null);
        comp2.setDiscontinued(null);
        comp2.setCompany(new Company(1));
        assertEquals(false,comp.equals(comp2));
    }
    

}
