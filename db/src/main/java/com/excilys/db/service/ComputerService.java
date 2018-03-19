package com.excilys.db.service;

import java.util.List;

import com.excilys.db.dao.ComputerDAO;
import com.excilys.db.exception.CompaniesInexistant;
import com.excilys.db.model.Computer;

public class ComputerService {



	private static ComputerDAO computer = ComputerDAO.getInstance();

	public static List<Computer> listComputer() throws CompaniesInexistant {
		return computer.listComputer();
	}

	public static int createComputer(Computer aAjouter){
		return computer.createAComputer(aAjouter);
	}

	public static void deleteComputer(int id) {
		computer.deleteAComputer(id);
	}

	public static Computer showDetails(int id) throws CompaniesInexistant {
		return computer.showDetails(id);
	}

	public static void updateAComputer(Computer aAjouter,int toUpdate) {
		computer.updateAComputer(aAjouter,toUpdate);
	}
}
