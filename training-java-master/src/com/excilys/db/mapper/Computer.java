package com.excilys.db.mapper;

import java.time.LocalDate;
import java.util.Date;

public class Computer {
	private int id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private int companyId;
	
	
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
	public void setIntroduced(Date date) {
		this.introduced = date;
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
	public void setCompanyId(String string) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder(200);
		sB.append(super.toString());
		sB.append(" name=").append(this.getName());
		sB.append(" introduced=").append(this.getIntroduced());
		sB.append(" discontinued=").append(this.getDiscontinued());
		sB.append(" companyId=").append(this.getCompanyId()); 
		return sB.toString();
		
	}

	
	
	
}
