package com.excilys.db.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.db.exception.CompaniesInexistant;
import com.excilys.db.model.Companies;
import com.excilys.db.persistance.DB_Connection;

/**
 * La classe DAO de Companies
 * @author flotte
 *
 */
public class CompaniesDAO{
	private static Connection conn;
	private static CompaniesDAO instance;
	
	/**
	 * Initialise la connection
	 */
	private static void init() {
		DB_Connection.getInstance().connect();
		conn = DB_Connection.getConn();
		
	}
	
    
    private CompaniesDAO() {
    }
    
    public static CompaniesDAO getInstance() {
		if (null == instance) {
			instance = new CompaniesDAO();
		}
    	return instance;
    	
    }
    
    /**
     * 
     * @param id d'une compagnie
     * @return true si la compagnie exist, false sinon
     */
    public static boolean existCompanies(int id) {
    	init();
		ResultSet resultSet = null;
		String querry = "SELECT name FROM company WHERE id = " + id;
		try {
			PreparedStatement prep1 = conn.prepareStatement(querry);
			resultSet = prep1.executeQuery();
			if (resultSet.next()) {
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DB_Connection.getInstance().disconnect();
		}
		DB_Connection.getInstance().disconnect();
		return false;
    }
	
    /**
     * 
     * @return la liste des compagnies
     */
	public List<Companies> listCompanies() {
		init();
		ResultSet resultSet = null;
		List<Companies> listResult = new ArrayList<Companies>();
		String querry = "SELECT name FROM company";
		try {
			PreparedStatement prep1 = conn.prepareStatement(querry);
			resultSet = prep1.executeQuery();
			while (resultSet.next()) {
				Companies toAdd = new Companies();
				toAdd.setName(resultSet.getString(1));
				listResult.add(toAdd);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DB_Connection.getInstance().disconnect();
		}
		DB_Connection.getInstance().disconnect();
		return listResult;
	}

	public Companies getCompanies(Integer id) throws CompaniesInexistant {
		init();
		ResultSet resultSet = null;
		Companies Result = new Companies();
		String querry;
		if (id != null) {
			querry = "SELECT name FROM company WHERE id = " + id;
		}else {
			Companies result = new Companies();
			result.setId(null);
			DB_Connection.getInstance().disconnect();
			return result;
		}
		try {
			PreparedStatement prep1 = conn.prepareStatement(querry);
			resultSet = prep1.executeQuery();
			if (resultSet.next()) {
				Companies toAdd = new Companies();
				toAdd.setName(resultSet.getString(1));
				toAdd.setId(id);
				return toAdd;
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DB_Connection.getInstance().disconnect();
		}
		DB_Connection.getInstance().disconnect();
		throw new CompaniesInexistant();
	}
	
}
