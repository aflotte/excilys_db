package com.excilys.db.service;

import java.util.List;
import java.util.Optional;

import com.excilys.db.dao.CompaniesDAO;
import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.model.Companies;

public enum CompaniesService {
	INSTANCE;
	private CompaniesDAO companies = CompaniesDAO.INSTANCE;

	public List<Companies> listCompanies() {
		return companies.listCompanies();
	}

	public Optional<Companies> getCompanies(int id) throws CompaniesInexistantException {
		return companies.getCompanies(id);
	}

}
