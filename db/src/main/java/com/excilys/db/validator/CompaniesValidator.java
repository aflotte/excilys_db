package com.excilys.db.validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.db.model.Computer;
import com.excilys.db.persistance.DB_Connection;

public class CompaniesValidator {
private static Connection conn;
	
	
	static Computer computer;
	
	
	private static void init() {
		DB_Connection.getInstance().connect();
		conn = DB_Connection.getConn();
		
	}
	
	public static boolean exist(int id) {
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
}
