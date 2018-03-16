package com.excilys.db.model;

import java.time.LocalDate;

import com.excilys.db.exception.CompaniesIdIncorrect;
import com.excilys.db.exception.CompaniesInexistant;
import com.excilys.db.exception.IncoherentDates;

public class Computer {
	private Integer id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Companies companie;
	
	public Computer() {
		companie = new Companies();
	}
	
	
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
	public LocalDate getIntroduced() {
		return introduced;
	}
	public void setIntroduced(LocalDate date) {
		this.introduced = date;
	}
	public LocalDate getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}
	public Companies getCompanyId() {
		return companie;
	}
	public void setCompanyId(Companies companie) throws IncoherentDates, CompaniesIdIncorrect, CompaniesInexistant {
		setCompanyId(companie.getId());
	}
	public void setCompanyId(Integer companyId) throws CompaniesInexistant {
		this.companie = new Companies(companyId);
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
		String CompIdtoPrint;
		if (this.getCompanyId() == null) {
			CompIdtoPrint = "null";
		}else {
			CompIdtoPrint = this.getCompanyId().getName();
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
		if (!this.getCompanyId().equals(((Computer) obj).getCompanyId())) {
			return false;
		}
		return true;
		
	}

	
	
	
}
