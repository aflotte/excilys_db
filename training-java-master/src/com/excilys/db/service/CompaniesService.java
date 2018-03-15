package com.excilys.db.service;

import java.util.List;

import com.excilys.db.DAO.CompaniesDAO;
import com.excilys.db.moddel.Companies;

public class CompaniesService {
	private static CompaniesDAO companies = CompaniesDAO.getInstance();
	
	public static List<Companies> listCompanies() {
		return companies.listCompanies();
	}
}
