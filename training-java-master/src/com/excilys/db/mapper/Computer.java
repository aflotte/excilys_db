package com.excilys.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Computer {
	private int id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Integer companyId;
	
	
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
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder(200);
		//sB.append(super.toString());
		sB.append(" | name=").append(this.getName());
		sB.append(" | introduced=").append(this.getIntroduced());
		sB.append(" | discontinued=").append(this.getDiscontinued());
		sB.append(" | companyId=").append(this.getCompanyId()); 
		return sB.toString();
		
	}
	
	public static Computer ResultToComputer(ResultSet resultSet) throws SQLException {
		Computer toReturn = new Computer();
		toReturn.setName(resultSet.getString(1));
		toReturn.setIntroduced(resultSet.getDate(2));
		toReturn.setIntroduced(resultSet.getDate(3));
		toReturn.setCompanyId(resultSet.getInt(4));
		return toReturn;
	}

	
	
	
}
