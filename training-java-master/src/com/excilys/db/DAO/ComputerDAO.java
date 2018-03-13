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

	
	private static ComputerDAO instance;
    
    private ComputerDAO() {
    	
    	
    }
    
    public static ComputerDAO getInstance() {
		if (null == instance) {
			instance = new ComputerDAO();
		}
    	return instance;
    	
    }
	
	
	
	
	public Computer ResultToComputer(ResultSet resultSet) throws SQLException {
		Computer toReturn = new Computer();
		toReturn.setName(resultSet.getString(1));
		toReturn.setIntroduced(resultSet.getDate(2));
		toReturn.setIntroduced(resultSet.getDate(3));
		toReturn.setCompanyId(resultSet.getInt(4));
		return toReturn;
	}
	
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
				Computer toAdd = ResultToComputer(resultSet);
				listResult.add(toAdd);
			}
			DB_Connection.getInstance().Disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DB_Connection.getInstance().Disconnect();
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
				result = ResultToComputer(resultSet);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DB_Connection.getInstance().Disconnect();
		}
		DB_Connection.getInstance().Disconnect();
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
			if (dateIntroduced == null) {
				ps.setNString(2,null);
			}else {
				ps.setString(2, sdf.format(dateIntroduced));
			}
			if (dateDiscontinued == null) {
				ps.setNString(3,null);
			}else {
				ps.setString(3, sdf.format(dateDiscontinued));
			}
			ps.setInt(4, computer.getCompanyId());
			ps.setInt(5,id);
			ps.executeUpdate();
			DB_Connection.getInstance().Disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DB_Connection.getInstance().Disconnect();
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
			if (dateIntroduced == null) {
				ps.setNString(2,null);
			}else {
				ps.setString(2, sdf.format(dateIntroduced));
			}
			if (dateDiscontinued == null) {
				ps.setNString(3,null);
			}else {
				ps.setString(3, sdf.format(dateDiscontinued));
			}
			ps.setInt(4, computer.getCompanyId());
			ps.executeUpdate();
			DB_Connection.getInstance().Disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DB_Connection.getInstance().Disconnect();
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
		String querryNotNULL = "SELECT id FROM computer WHERE name = ? AND introduced = ? AND discontinued = ? AND company_id = ?";
		String querryIntroducedNULL = "SELECT id FROM computer WHERE name = ? AND introduced is ? AND discontinued = ? AND company_id = ?";
		String querryDiscontinuedNULL = "SELECT id FROM computer WHERE name = ? AND introduced = ? AND discontinued is ? AND company_id = ?";
		String querryBothNULL = "SELECT id FROM computer WHERE name = ? AND introduced is ? AND discontinued is ? AND company_id = ?";
		// choice of the good query
		String querry;
		if(dateIntroduced == null) {
			if(dateDiscontinued == null) {
				querry = querryBothNULL;
			}else {
				querry = querryIntroducedNULL;
			}
		}else {
			if(dateDiscontinued == null) {
				querry = querryNotNULL;
			}else {
				querry = querryDiscontinuedNULL;
			}
		}
		try {
			PreparedStatement ps = conn.prepareStatement(querry);
			ps.setString(1, computer.getName());
			if (dateIntroduced == null) {
				ps.setNString(2,null);
			}else {
				ps.setString(2, sdf.format(dateIntroduced));
			}
			if (dateDiscontinued == null) {
				ps.setNString(3,null);
			}else {
				ps.setString(3, sdf.format(dateDiscontinued));
			}
			ps.setInt(4, computer.getCompanyId());
			resultSet = ps.executeQuery();
			while (resultSet.next()) {
				result.add(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DB_Connection.getInstance().Disconnect();
		}
		DB_Connection.getInstance().Disconnect();
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
			DB_Connection.getInstance().Disconnect();
		}
		DB_Connection.getInstance().Disconnect();
		return result;
	
	}
	
	

	public void deleteAComputer(int id) {
		
		DB_Connection.getInstance().Connection();
		Connection conn = DB_Connection.getConn();
		String querry = "DELETE FROM computer WHERE id = "+ id;
		try {
			PreparedStatement prep1 = conn.prepareStatement(querry);
			prep1.executeUpdate(querry);
			DB_Connection.getInstance().Disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DB_Connection.getInstance().Disconnect();
		}
	}

}
