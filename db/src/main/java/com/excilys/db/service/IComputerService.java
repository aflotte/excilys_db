package com.excilys.db.service;

import java.util.List;
import java.util.Optional;


import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.exception.ServiceException;
import com.excilys.db.model.Computer;
import com.excilys.db.page.PageComputerDTO;

public interface IComputerService {

    /**
     *
     * @return la liste des ordinateurs
     * @throws CompaniesInexistantException un ordinateur a été mal formé
     */
    List<Computer> listComputer();

    /**
     *
     * @param offset l'offset
     * @param limit la limite
     * @param sortBy sortBy
     * @param orderBy l'ordre
     * @return la liste des ordinateurs
     * @throws CompaniesInexistantException erreurs lors de la création de l'ordinateur
     */
    List<Computer> listComputer(int offset, int limit, String sortBy, String orderBy);

    List<Computer> listComputer(PageComputerDTO page);

    /**
     *
     * @param offset l'offset
     * @param limit la limite
     * @return la liste des ordinateurs
     */
    List<Computer> listComputer(int offset, int limit);

    /**
     *
     * @param aAjouter ordinateur à ajouter
     * @return l'id de l'ordinateur
     * @throws ServiceException l'exception du service
     */
    int createComputer(Computer aAjouter) throws ServiceException;

    /**
     *
     * @param id de l'odinateur à supprimer
     */
    void deleteComputer(int id);

    /**
     *
     * @param id de l'ordinateur dont on veut les informations
     * @return l'ordinateur
     * @throws ServiceException l'exception du service
     */
    Optional<Computer> showDetails(int id) throws ServiceException;

    /**
     *
     * @param aAjouter l'ordinateur a ajouter dans la base de donnée
     * @param toUpdate l'id de l'ordinateur à mettre à jour
     * @throws ServiceException l'exception du service
     */
    void updateAComputer(Computer aAjouter, int toUpdate) throws ServiceException;

    /**
     *
     * @return le nombre d'ordinateur
     */
    int getCount();

    /**
     *
     * @param search search
     * @return l'ordi
     */
    int getCount(String search);

    /**
     *
     * @param offset l'offset
     * @param limit la limite
     * @param name le nom
     * @param sortBy sortBy
     * @param orderBy ordre
     * @return la liste des ordinateurs
     */
    List<Computer> listComputerLike(int offset, int limit, String name, String sortBy, String orderBy);

    /**
     *
     * @param offset l'offset
     * @param limit la limite
     * @param name le nom
     * @return la liste des ordinateurs
     */
    List<Computer> listComputerLike(int offset, int limit, String name);

    /**
     *
     * @param ids ids à delete
     */
    void deleteListComputer(int[] ids);

}