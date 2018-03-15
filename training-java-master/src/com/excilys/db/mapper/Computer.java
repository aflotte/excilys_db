package com.excilys.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Computer {
	private Integer id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Integer companyId;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
		/*if(this.getId()!= null) {
			sB.append(" | id=").append(this.getId());	
		}*/
		
		sB.append(" | name=").append(this.getName());
		sB.append(" | introduced=").append(this.getIntroduced());
		sB.append(" | discontinued=").append(this.getDiscontinued());
		Integer CompIdtoPrint;
		if (this.getCompanyId() == 0) {
			CompIdtoPrint = null;
		}else {
			CompIdtoPrint = this.getCompanyId();
		}
		sB.append(" | companyId=").append(CompIdtoPrint); 
		return sB.toString();
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			return true;
		}
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (!this.getName().equals(((Computer) obj).getName())) {
			return false;
		}
		if (this.getIntroduced() != ((Computer) obj).getIntroduced()) {
			return false;
		}
		if (this.getDiscontinued() != ((Computer) obj).getDiscontinued()) {
			return false;
		}
		if (this.getCompanyId() != ((Computer) obj).getCompanyId()) {
			return false;
		}
		return true;
		
	}
	
	
	public static Computer resultToComputer(ResultSet resultSet) throws SQLException {
		Computer toReturn = new Computer();
		toReturn.setName(resultSet.getString(1));
		toReturn.setIntroduced(resultSet.getDate(2));
		toReturn.setIntroduced(resultSet.getDate(3));
		toReturn.setCompanyId(resultSet.getInt(4));
		toReturn.setId(resultSet.getInt(5));
		return toReturn;
	}

	
	
	
}
