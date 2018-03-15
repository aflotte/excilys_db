package com.excilys.db.service;

import java.util.List;

import com.excilys.db.DAO.ComputerDAO;
import com.excilys.db.moddel.Computer;

public class ComputerService {
	
	
	
	private static ComputerDAO computer = ComputerDAO.getInstance();
	
	public static List<Computer> listComputer() {
		return computer.listComputer();
	}
	
	public static int createComputer(Computer aAjouter){
		return computer.createAComputer(aAjouter);
	}
	
	public static void deleteComputer(int id) {
		computer.deleteAComputer(id);
	}
	
	public static Computer showDetails(int id) {
		return computer.showDetails(id);
	}
	
	public static void updateAComputer(Computer aAjouter,int toUpdate) {
		computer.updateAComputer(aAjouter,toUpdate);
	}
}
