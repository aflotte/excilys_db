package com.excilys.db.service;

import java.util.List;
import java.util.Optional;

import com.excilys.db.dao.ComputerDAO;
import com.excilys.db.exception.CompaniesIdIncorrectException;
import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.exception.IncoherentDatesException;
import com.excilys.db.model.Computer;
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
    public List<Computer> listComputer() throws CompaniesInexistantException {
        return computer.listComputer();
    }

    /**
     * 
     * @param offset l'offset
     * @param limit la limite
     * @return la liste des ordinateurs
     * @throws CompaniesInexistantException erreurs lors de la création de l'ordinateur
     */
    public List<Computer> listComputer(int offset, int limit) {
        return computer.listComputer(offset, limit);
    }

    /**
     *
     * @param aAjouter ordinateur à ajouter
     * @return l'id de l'ordinateur
     */
    public int createComputer(Computer aAjouter) {
        try {
            if (ComputerValidator.INSTANCE.validate(aAjouter)) {
                return computer.createAComputer(aAjouter);
            }
        } catch (IncoherentDatesException | CompaniesIdIncorrectException e) {
            logger.warn(e.getMessage());
        }
        //TODO: lever une exeption
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
     * @throws CompaniesInexistantException l'ordinateur a été mal formé au niveau de sa compagnie
     */
    public Optional<Computer> showDetails(int id) throws CompaniesInexistantException {
        return computer.showDetails(id);
    }

    /**
     *
     * @param aAjouter l'ordinateur a ajouter dans la base de donnée
     * @param toUpdate l'id de l'ordinateur à mettre à jour
     */
    public void updateAComputer(Computer aAjouter, int toUpdate) {
        try {
            if (ComputerValidator.INSTANCE.validate(aAjouter)) {
                computer.updateAComputer(aAjouter, toUpdate);
            }
        } catch (IncoherentDatesException | CompaniesIdIncorrectException e) {
            logger.warn(e.getMessage());
        }
    }
}