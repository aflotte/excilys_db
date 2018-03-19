package com.excilys.db.service;

import java.util.List;

import com.excilys.db.dao.ComputerDAO;
import com.excilys.db.exception.CompaniesIdIncorrect;
import com.excilys.db.exception.CompaniesInexistant;
import com.excilys.db.exception.IncoherentDates;
import com.excilys.db.model.Computer;
import com.excilys.db.validator.ComputerValidator;

public enum ComputerService {
	INSTANCE;

	static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerService.class);
	private ComputerDAO computer = ComputerDAO.INSTANCE;

	public List<Computer> listComputer() throws CompaniesInexistant {
		return computer.listComputer();
	}

	public int createComputer(Computer aAjouter){
		try {
			if (ComputerValidator.INSTANCE.validate(aAjouter)) {
				return computer.createAComputer(aAjouter);
			}
		} catch (IncoherentDates | CompaniesIdIncorrect e) {
			logger.warn(e.getMessage());
		}
		return 0;
	}

		public void deleteComputer(int id) {
			computer.deleteAComputer(id);
		}

		public Computer showDetails(int id) throws CompaniesInexistant {
			return computer.showDetails(id);
		}

		public void updateAComputer(Computer aAjouter,int toUpdate) {
			try {
				if (ComputerValidator.INSTANCE.validate(aAjouter)) {
					computer.updateAComputer(aAjouter,toUpdate);
				}
			} catch (IncoherentDates | CompaniesIdIncorrect e) {
				logger.warn(e.getMessage());
			}
		}
	}
