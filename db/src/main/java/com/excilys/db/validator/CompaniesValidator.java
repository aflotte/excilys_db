package com.excilys.db.validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.db.model.Companies;
import com.excilys.db.model.Computer;
import com.excilys.db.persistance.DB_Connection;

public enum CompaniesValidator {
	INSTANCE;
	static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompaniesValidator.class);
	private static Connection conn;


	static Computer computer;


	private void init() {
		DB_Connection.getInstance().connect();
		conn = DB_Connection.getConn();

	}

	public boolean exist(int id) {
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
			logger.warn(e.getMessage());
		}finally {
			DB_Connection.getInstance().disconnect();	
		}
		
		return false;
	}
	
	public boolean check(Companies companie) {
		init();
		ResultSet resultSet = null;
		if (companie.getId()==null) {
			if (companie.getName().equals("")) {
				return true;
			}else {
				return false;
			}
		}
		String querry = "SELECT name FROM company WHERE id = " + companie.getId();
		try {
			PreparedStatement prep1 = conn.prepareStatement(querry);
			resultSet = prep1.executeQuery();
			if (resultSet.next()) {
				if (resultSet.getString(1).equals(companie.getName())) {
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage());
		}finally {
			DB_Connection.getInstance().disconnect();	
		}
		
		return false;
	}
}