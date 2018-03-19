package com.excilys.db.service;

import java.util.List;

import com.excilys.db.dao.CompaniesDAO;
import com.excilys.db.exception.CompaniesInexistant;
import com.excilys.db.model.Companies;

public enum CompaniesService {
	INSTANCE;
	private CompaniesDAO companies = CompaniesDAO.INSTANCE;

	public List<Companies> listCompanies() {
		return companies.listCompanies();
	}

	public Companies getCompanies(int id) throws CompaniesInexistant {
		return companies.getCompanies(id);
	}

}
