package com.excilys.db.service;

import java.util.List;
import java.util.Optional;

import com.excilys.db.exception.CompaniesIdIncorrectException;
import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.exception.DAOAccesExeption;
import com.excilys.db.exception.IncoherentDatesException;
import com.excilys.db.exception.ServiceException;
import com.excilys.db.exception.ValidatorException;
import com.excilys.db.model.Computer;
import com.excilys.db.persistance.ComputerDAO;
import com.excilys.db.validator.ComputerValidator;

public enum ComputerService {
    INSTANCE;
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerService.class);
    private ComputerDAO computer = ComputerDAO.INSTANCE;

    /**
     *
     * @return la liste des ordinateurs
     * @throws CompaniesInexistantException un ordinateur a été mal formé
     */
    public List<Computer> listComputer() {
        return computer.listComputer();
    }

    /**
     *
     * @param offset l'offset
     * @param limit la limite
     * @param sortBy sortBy
     * @param orderBy l'ordre
     * @return la liste des ordinateurs
     * @throws CompaniesInexistantException erreurs lors de la création de l'ordinateur
     */
    public List<Computer> listComputer(int offset, int limit, String sortBy, String orderBy) {
        return computer.listComputer(offset, limit, sortBy, orderBy);
    }

    /**
     *
     * @param offset l'offset
     * @param limit la limite
     * @return la liste des ordinateurs
     */
    public List<Computer> listComputer(int offset, int limit) {
        return computer.listComputer(offset, limit, "computer.id", "asc");
    }

    /**
     *
     * @param aAjouter ordinateur à ajouter
     * @return l'id de l'ordinateur
     * @throws ServiceException l'exception du service
     */
    public int createComputer(Computer aAjouter) throws ServiceException {
        try {
            if (ComputerValidator.INSTANCE.validate(aAjouter)) {
                return computer.createAComputer(aAjouter);
            }
        } catch (IncoherentDatesException | CompaniesIdIncorrectException | DAOAccesExeption | ValidatorException e) {
            logger.warn(e.getMessage());
            throw new ServiceException();
        }
        return 0;
    }

    /**
     *
     * @param id de l'odinateur à supprimer
     */
    public void deleteComputer(int id) {
        computer.deleteAComputer(id);
    }

    /**
     *
     * @param id de l'ordinateur dont on veut les informations
     * @return l'ordinateur
     * @throws ServiceException l'exception du service
     */
    public Optional<Computer> showDetails(int id) throws ServiceException {
        try {
            return computer.showDetails(id);
        } catch (DAOAccesExeption e) {
            logger.warn(e.getMessage());
            throw new ServiceException();
        }
    }

    /**
     *
     * @param aAjouter l'ordinateur a ajouter dans la base de donnée
     * @param toUpdate l'id de l'ordinateur à mettre à jour
     * @throws ServiceException l'exception du service
     */
    public void updateAComputer(Computer aAjouter, int toUpdate) throws ServiceException {
        try {
            if (ComputerValidator.INSTANCE.validate(aAjouter)) {
                computer.updateAComputer(aAjouter, toUpdate);
            }
        } catch (IncoherentDatesException | CompaniesIdIncorrectException | DAOAccesExeption | ValidatorException e) {
            logger.warn(e.getMessage());
            throw new ServiceException();
        }
    }

    /**
     *
     * @return le nombre d'ordinateur
     */
    public int getCount() {
        return computer.getCount();
    }

    /**
     *
     * @param search search
     * @return l'ordi
     */
    public int getCount(String search) {
        return computer.getCount(search);
    }

    /**
     *
     * @param offset l'offset
     * @param limit la limite
     * @param name le nom
     * @param sortBy sortBy
     * @param orderBy ordre
     * @return la liste des ordinateurs
     */
    public List<Computer> listComputerLike(int offset, int limit, String name, String sortBy, String orderBy) {
        return computer.listComputerLike(offset, limit, name, sortBy, orderBy);
    }

    /**
     *
     * @param offset l'offset
     * @param limit la limite
     * @param name le nom
     * @return la liste des ordinateurs
     */
    public List<Computer> listComputerLike(int offset, int limit, String name) {
        return computer.listComputerLike(offset, limit, name, "computer.id", "asc");
    }

    /**
     *
     * @param ids ids à delete
     */
    public void deleteListComputer(int[] ids) {
        computer.deleteListComputer(ids);
    }
}