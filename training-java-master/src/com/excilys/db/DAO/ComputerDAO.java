package com.excilys.db.DAO;

import java.util.Date;

import com.excilys.db.mapper.Computer;

public interface ComputerDAO {
	

	public Computer[] listComputer();
	
	public String showDetails(Computer comp);
	
	public void updateAComputer();
	
	public void createAComputer();
	
	public void deleteAComputer();
	
	
	

}
