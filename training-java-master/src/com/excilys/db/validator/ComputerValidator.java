package com.excilys.db.validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.excilys.db.exception.CompaniesIdIncorrect;
import com.excilys.db.exception.IncoherentDates;
import com.excilys.db.moddel.Computer;
import com.excilys.db.persistance.DB_Connection;

public class ComputerValidator {
	
	private static Connection conn;
	
	
	static Computer computer;
	
	
	private static void init() {
		DB_Connection.getInstance().connect();
		conn = DB_Connection.getConn();
		
	}
	
	public static void init(Computer instance) {
		computer = instance;
	}
	
	public static boolean exist(int id) {
		init();
		ResultSet resultSet = null;
		String querry = "SELECT name FROM computer WHERE id = " + id;
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
	
	
	public static boolean testDate() {
		Date introduced = computer.getIntroduced();
		Date discontinued = computer.getDiscontinued();
		return ((introduced != null)&&(discontinued != null)&&(introduced.compareTo(discontinued)>0));
	}
	
	public static boolean testIdCompanie() {
    	init();
		ResultSet resultSet = null;
		if (computer.getCompanyId()==null) {
			return true;
		}
		String querry = "SELECT name FROM company WHERE id = " + computer.getCompanyId();
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
	
	public static boolean validate() throws IncoherentDates, CompaniesIdIncorrect {
		if (testDate()) {
			throw new IncoherentDates();
		}
		if (!testIdCompanie()) {
			throw new CompaniesIdIncorrect();
		}
		return (testDate()&&testIdCompanie());
	}
}
