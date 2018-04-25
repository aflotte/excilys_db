package com.excilys.db.service;

import java.util.List;
import java.util.Optional;

import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.exception.ValidatorException;
import com.excilys.db.model.Company;

public interface ICompaniesService extends CompagnyIdable {

    /**
     *
     * @return la liste des compagnies
     */
    List<Company> listCompanies();

    /**
     *
     * @param id de la compagnie
     * @return la compagnie
     * @throws CompaniesInexistantException si la compagnie n'existe pas
     */
    Optional<Company> getCompanies(int id) throws CompaniesInexistantException;

    /**
     *
     * @param name le nom de la compagnie
     * @return l'id de la compagnie
     */
    Integer getCompagnyId(String name);

    
    void destroy(int id) throws ValidatorException;
}
