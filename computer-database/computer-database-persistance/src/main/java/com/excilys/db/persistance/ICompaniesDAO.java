package com.excilys.db.persistance;

import java.util.List;
import java.util.Optional;

import com.excilys.db.model.Company;

public interface ICompaniesDAO {

    /**
     * 
     * @param id id de la compagnie
     * @return
     */
    List<Integer> computerFromCompany(int id);

    /**
     *
     * @param id d'une compagnie
     * @return true si la compagnie exist, false sinon
     */
    boolean existCompanies(int id);

    /**
     *
     * @return la liste des compagnies
     */
    List<Company> listCompanies();

    /**
     *
     * @param id l'id
     */
    void deleteCompany(int id);

    /**
     *
     * @param offset l'offset
     * @param limit le nombre a afficher
     * @return la liste des compagnies
     * @throws CompaniesInexistantException erreur sur la compagnie de l'ordinateur
     */
    List<Company> listCompany(int offset, int limit);

    /**
     *
     * @param id de la compagnie
     * @return la compagnie
     * @throws CompaniesInexistantException la compagnie recherché n'existe pas
     */
    Optional<Company> getCompany(Integer id);

    /**
     *
     */
    int getCount();

    /**
     *
     * @param name le nom de l'ordinateur
     * @return la liste des Id
     */
    List<Integer> getIdFromName(String name);

}