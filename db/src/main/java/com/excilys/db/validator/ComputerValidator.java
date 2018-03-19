package com.excilys.db.validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.excilys.db.exception.CompaniesIdIncorrect;
import com.excilys.db.exception.IncoherentDates;
import com.excilys.db.model.Computer;
import com.excilys.db.persistance.DB_Connection;

public enum ComputerValidator {
	INSTANCE;
	
	private static Connection conn;
	private ComputerValidator() {
	
	}


	private static void init() {
		DB_Connection.getInstance().connect();
		conn = DB_Connection.getConn();

	}


	public boolean exist(int id) {
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


	public boolean testDate(Computer computer) {
		LocalDate introduced = computer.getIntroduced();
		LocalDate discontinued = computer.getDiscontinued();
		return ((introduced != null)&&(discontinued != null)&&(introduced.compareTo(discontinued)>0));
	}

	public static boolean testIdCompanie(Computer computer) {
		init();
		ResultSet resultSet = null;
		if (computer.getCompanyId().getId()==null) {
			return true;
		}
		String querry = "SELECT name FROM company WHERE id = " + computer.getCompanyId().getId();
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

	public boolean validate(Computer computer) throws IncoherentDates, CompaniesIdIncorrect {
		if (testDate(computer)) {
			throw new IncoherentDates();
		}
		if (!testIdCompanie(computer)) {
			throw new CompaniesIdIncorrect();
		}
		return true;
	}
}
