package com.excilys.db.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.excilys.db.mapper.Computer;
import com.excilys.db.persistance.DB_Connection;

public class ComputerDAO {

	public List<Computer> listComputer() {
		DB_Connection.getInstance().Connection();
		Connection conn = DB_Connection.getConn();
		ResultSet resultSet = null;
		List<Computer> listResult = new ArrayList<Computer>();
		String querry = "SELECT name, introduced, discontinued, company_id FROM computer";
		try {
			PreparedStatement prep1 = conn.prepareStatement(querry);
			resultSet = prep1.executeQuery();
			while (resultSet.next()) {
				Computer toAdd = new Computer();
				toAdd.setName(resultSet.getString(1));
				toAdd.setIntroduced(resultSet.getDate(2));
				toAdd.setDiscontinued(resultSet.getDate(3));
				toAdd.setCompanyId(resultSet.getInt(4));
				listResult.add(toAdd);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listResult;
	}

	public Computer showDetails(int id) {
		DB_Connection.getInstance().Connection();
		Connection conn = DB_Connection.getConn();
		ResultSet resultSet = null;
		Computer result = new Computer();
		String querry = "SELECT name, introduced, discontinued, company_id FROM computer WHERE id = " + id;
		try {
			PreparedStatement prep1 = conn.prepareStatement(querry);
			resultSet = prep1.executeQuery();
			if (resultSet.next()) {
				result.setName(resultSet.getString(1));
				//TODO: gérer la date nulle
				result.setIntroduced(resultSet.getDate(2));
				result.setDiscontinued(resultSet.getDate(3));
				result.setCompanyId(resultSet.getInt(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public void updateAComputer(Computer comp, int id) {	
		DB_Connection.getInstance().Connection();
		Connection conn = DB_Connection.getConn();
		String querry = "UPDATE computer SET name = " + comp.getName() + ", introduced = "+ comp.getIntroduced() + ", discontinued = " + comp.getDiscontinued() + ", company_id = " + comp.getCompanyId() +" WHERE id = "+ id;
		try {
			PreparedStatement prep1 = conn.prepareStatement(querry);
			prep1.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createAComputer(Computer computer) {
		DB_Connection.getInstance().Connection();
		Connection conn = DB_Connection.getConn();
		String querry = "INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (" + computer.getName() + "," + computer.getIntroduced() + "," + computer.getDiscontinued() + "," + computer.getCompanyId() + ")";
		try {
			PreparedStatement prep1 = conn.prepareStatement(querry);
			prep1.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteAComputer(int id) {
		
		DB_Connection.getInstance().Connection();
		Connection conn = DB_Connection.getConn();
		String querry = "DELETE FROM computer WHERE id = "+ id;
		try {
			PreparedStatement prep1 = conn.prepareStatement(querry);
			prep1.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
