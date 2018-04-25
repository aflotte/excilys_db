package com.excilys.db;

import com.excilys.db.model.Company;

import junit.framework.TestCase;

public class CompaniesTest extends TestCase {

    public void testToString() {
        Company comp = new Company();
        comp.setName("Name");
        assertEquals(" | id=null | name=Name",comp.toString());
    }

    public void testEqualsTrue() {
        Company comp = new Company();
        comp.setName("Name");
        Company comp2 = new Company();
        comp2.setName("Name");
        assertEquals(true,comp.equals(comp2));
    }

    public void testEqualsFalseNull() {
        Company comp = new Company();
        comp.setName("Name");
        Company comp2 = null;
        assertEquals(false,comp.equals(comp2));
    }

    public void testEqualsSame() {
        Company comp = new Company();
        comp.setName("Name");
        assertEquals(true,comp.equals(comp));
    }

    @SuppressWarnings("unlikely-arg-type")
    public void testEqualsFalseClass() {
        Company comp = new Company();
        comp.setName("Name");
        Integer comp2 = new Integer(5);
        assertEquals(false,comp.equals(comp2));
    }

    public void testEqualsFalseName() {
        Company comp = new Company();
        comp.setName("Name");
        Company comp2 = new Company();
        comp2.setName("Name2");
        assertEquals(false,comp.equals(comp2));
    }

    public void testEqualsFalseNullId() {
        Company comp = new Company();
        comp.setName("Name");
        Company comp2 = new Company();
        comp2.setName("Name");
        comp2.setId(1);
        assertEquals(false,comp.equals(comp2));
    }
}
