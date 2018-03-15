package com.excilys.db.test;

import org.junit.Test;

import com.excilys.db.model.Companies;
import com.excilys.db.model.Computer;

import junit.framework.TestCase;

public class CompaniesTest extends TestCase {

	@Test
	public void testToString() {
		Companies comp = new Companies();
		comp.setName("Name");
		assertEquals(comp.toString()," | name=Name");
	}
	
	@Test
	public void testEqualsTrue() {
		Companies comp = new Companies();
		comp.setName("Name");
		Companies comp2 = new Companies();
		comp2.setName("Name");
		assertEquals(true,comp.equals(comp2));
	}
	
	@Test
	public void testEqualsSame() {
		Companies comp = new Companies();
		comp.setName("Name");
		assertEquals(true,comp.equals(comp));
	}
	
	@Test
	public void testEqualsFalseClass() {
		Companies comp = new Companies();
		comp.setName("Name");
		Integer comp2 = new Integer(5);
		assertEquals(false,comp.equals(comp2));
	}
	
	@Test
	public void testEqualsFalseName() {
		Companies comp = new Companies();
		comp.setName("Name");
		Companies comp2 = new Companies();
		comp2.setName("Name2");
		assertEquals(false,comp.equals(comp2));
	}
}
