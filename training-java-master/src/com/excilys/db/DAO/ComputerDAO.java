package com.excilys.db.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
				toAdd.setIntroduced(resultSet.getDate(3));
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

	public void updateAComputer(Computer computer, int id) {	
		DB_Connection.getInstance().Connection();
		Connection conn = DB_Connection.getConn();
		java.util.Date dateIntroduced = computer.getIntroduced();
		java.util.Date dateDiscontinued = computer.getDiscontinued();

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String querry = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(querry);
			ps.setString(1, computer.getName());
		
			ps.setString(2, sdf.format(dateIntroduced));
			ps.setString(3, sdf.format(dateDiscontinued));
			ps.setInt(4, computer.getCompanyId());
			ps.setInt(5,id);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createAComputer(Computer computer) {
		DB_Connection.getInstance().Connection();
		Connection conn = DB_Connection.getConn();
		java.util.Date dateIntroduced = computer.getIntroduced();
		java.util.Date dateDiscontinued = computer.getDiscontinued();

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String querry = "INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(querry);
			ps.setString(1, computer.getName());
		
			ps.setString(2, sdf.format(dateIntroduced));
			ps.setString(3, sdf.format(dateDiscontinued));
			ps.setInt(4, computer.getCompanyId());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public List<Integer> getId(Computer computer) {
		List<Integer> result = new ArrayList<Integer>();
		DB_Connection.getInstance().Connection();
		Connection conn = DB_Connection.getConn();
		ResultSet resultSet = null;
		java.util.Date dateIntroduced = computer.getIntroduced();
		java.util.Date dateDiscontinued = computer.getDiscontinued();

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String querry = "SELECT id FROM computer WHERE name = ? AND introduced = ? AND discontinued = ? AND company_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(querry);
			ps.setString(1, computer.getName());
			
			ps.setString(2, sdf.format(dateIntroduced));
			ps.setString(3, sdf.format(dateDiscontinued));
			ps.setInt(4, computer.getCompanyId());
			resultSet = ps.executeQuery();
			while (resultSet.next()) {
				result.add(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	
	}
	
	
	public List<Integer> getIdFromName(String name) {
		List<Integer> result = new ArrayList<Integer>();
		DB_Connection.getInstance().Connection();
		Connection conn = DB_Connection.getConn();
		ResultSet resultSet = null;
		String querry = "SELECT id FROM computer WHERE name = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(querry);
			ps.setString(1, name);

			resultSet = ps.executeQuery();
			while (resultSet.next()) {
				result.add(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	
	}
	
	

	public void deleteAComputer(int id) {
		
		DB_Connection.getInstance().Connection();
		Connection conn = DB_Connection.getConn();
		String querry = "DELETE FROM computer WHERE id = "+ id;
		try {
			PreparedStatement prep1 = conn.prepareStatement(querry);
			prep1.executeUpdate(querry);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
