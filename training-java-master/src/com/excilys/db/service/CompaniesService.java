package com.excilys.db.service;

import java.util.List;

import com.excilys.db.DAO.CompaniesDAO;
import com.excilys.db.exception.CompaniesInexistant;
import com.excilys.db.model.Companies;

public class CompaniesService {
	private static CompaniesDAO companies = CompaniesDAO.getInstance();
	
	public static List<Companies> listCompanies() {
		return companies.listCompanies();
	}
	
	public static Companies getCompanies(int id) throws CompaniesInexistant {
		return companies.getCompanies(id);
	}
	
}
