package com.excilys.db.DAO;

import java.util.Date;

import com.excilys.db.mapper.Computer;

public class ComputerDAO {
	private int id;
	private String name;
	private Date introduced;
	
	//TODO : faire la fonction
	public Computer[] listComputer() {
		return new Computer[1];
	}
	
	//TODO : faire la fonction
	public void updateAComputer() {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getIntroduced() {
		return introduced;
	}
	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}
	public Date getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	private Date discontinued;
	private int companyId;
}
