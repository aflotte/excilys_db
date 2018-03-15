package com.excilys.db.model;

import com.excilys.db.exception.CompaniesInexistant;
import com.excilys.db.service.CompaniesService;

public class Companies {
	private Integer id;
	private String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Companies() {
		super();
	}
	
	public Companies(Integer tid) throws CompaniesInexistant {
		id = tid;
		name = CompaniesService.getCompanies(id).getName();
	}
	
	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder(200);
		// sB.append(super.toString());
		sB.append(" | name=").append(this.getName());
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
		if (!this.getName().equals(((Companies) obj).getName())) {
			return false;
		}
		return true;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
