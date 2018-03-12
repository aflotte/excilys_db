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

	public void updateAComputer() {
		// TODO Auto-generated method stub
		
	}

	public void createAComputer(String name, Date introduced, Date discontinued, int company) {
		// TODO Auto-generated method stub
		
	}

	public void deleteAComputer() {
		// TODO Auto-generated method stub
		
	}

}
