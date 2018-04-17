package com.excilys.db.persistance;

import java.util.List;
import java.util.Optional;

import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.model.Computer;
import com.excilys.db.page.PageComputerDTO;

public interface IComputerDAO {

    /**
     *
     * @return la liste des ordinateurs
     * @throws CompaniesInexistantException erreur sur la compagnie de l'ordinateur
     */
    List<Computer> listComputer();

    /**
     *
     * @param offset l'offset
     * @param limit le nombre a afficher
     * @return la liste des ordinateurs
     * @throws CompaniesInexistantException erreur sur la compagnie de l'ordinateur
     */
    List<Computer> listComputer(int offset, int limit, String sortBy, String orderBy);

    /**
        *
        * @param offset l'offset
        * @param limit le nombre a afficher
        * @return la liste des ordinateurs
        * @throws CompaniesInexistantException erreur sur la compagnie de l'ordinateur
        */
    List<Computer> listComputer(PageComputerDTO page);

    /**
     *
     * @param id de l'ordinateur
     * @return un ordinateur
     * @throws CompaniesInexistantException si la compagnie de l'ordinateur est inexistante
     */
    Optional<Computer> showDetails(int id);

    /**
     *
     * @param computer l'ordinateur qui remplacera l'ancien
     * @param id de l'ordinateur a remplacer
     */
    void updateAComputer(Computer computer, int id);

    /**
     *
     * @param computer l'ordinateur a ajouter
     * @return l'Id de l'ordinateur qui vient d'être ajouter
     */
    int createAComputer(Computer computer);

    /**
     *
     * @param name le nom de l'ordinateur
     * @return la liste des Id
     */
    List<Integer> getIdFromName(String name);

    /**
     *
     * @param id de l'ordinateur a supprimer
     */
    void deleteAComputer(int id);

    /**
     *
     * @param ids la liste des ids
     */
    void deleteListComputer(List<Integer> ids);

    /**
     *
     */
    int getCount();

    /**
     *
     */
    int getCount(String search);

    List<Computer> listComputerLike(int offset, int limit, String name, String sortBy, String orderBy);

    List<Computer> listComputerLike(PageComputerDTO page);
}