package com.excilys.db.service;

import java.util.List;
import java.util.Optional;

import com.excilys.db.dao.CompaniesDAO;
import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.model.Company;

public enum CompaniesService {
    INSTANCE;
    private CompaniesDAO companies = CompaniesDAO.INSTANCE;

    /**
     *
     * @return la liste des compagnies
     */
    public List<Company> listCompanies() {
        return companies.listCompanies();
    }

    /**
     *
     * @param id de la compagnie
     * @return la compagnie
     * @throws CompaniesInexistantException si la compagnie n'existe pas
     */
    public Optional<Company> getCompanies(int id) throws CompaniesInexistantException {
        return companies.getCompany(id);
    }

    
    public Integer getCompagnyId(String name) {
        //TODO: error case liste vide
        return companies.getIdFromName(name).get(0);
    }
}