package com.excilys.db.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.db.mapper.Companies;
import com.excilys.db.persistance.DB_Connection;

public class CompaniesDAO{
	private static Connection conn;
	
	private static void Init() {
		DB_Connection.getInstance().connect();
		conn = DB_Connection.getConn();
		
	}
	private static CompaniesDAO instance;
    
    private CompaniesDAO() {
    	
    	
    }
    
    public static CompaniesDAO getInstance() {
		if (null == instance) {
			instance = new CompaniesDAO();
		}
    	return instance;
    	
    }
    
    public static boolean existCompanies(int id) {
    	Init();
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
	
	public List<Companies> listCompanies() {
		Init();
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

}
