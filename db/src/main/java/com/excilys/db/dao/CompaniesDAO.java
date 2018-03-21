package com.excilys.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.model.Companies;
import com.excilys.db.persistance.DB_Connection;

/**
 * La classe DAO de Companies
 * @author flotte
 *
 */
public enum CompaniesDAO{
	INSTANCE;
	static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompaniesDAO.class);
	private static Connection conn;

	private final static String QUERRY_LIST_COMPANIES = "SELECT name FROM company";
	private final static String QUERRY_LIST_COMPANIES_ID = "SELECT name FROM company WHERE id = ";

	/**
	 * Initialise la connection
	 */
	private void init() {
		DB_Connection.getInstance().connect();
		conn = DB_Connection.getConn();

	}


	private CompaniesDAO() {
	}


	/**
	 * 
	 * @param id d'une compagnie
	 * @return true si la compagnie exist, false sinon
	 */
	public boolean existCompanies(int id) {
		init();
		ResultSet resultSet = null;
		try {
			PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPANIES_ID + id);
			logger.debug("Requête : " + prep1.toString());
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

	/**
	 * 
	 * @return la liste des compagnies
	 */
	public List<Companies> listCompanies() {
		init();
		ResultSet resultSet = null;
		List<Companies> listResult = new ArrayList<Companies>();
		try {
			PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPANIES);
			logger.debug("Requête : " + prep1.toString());
			resultSet = prep1.executeQuery();
			while (resultSet.next()) {
				Companies toAdd = new Companies();
				toAdd.setName(resultSet.getString(1));
				listResult.add(toAdd);
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage());
		}finally {
			DB_Connection.getInstance().disconnect();
		}
		return listResult;
	}

	public Optional<Companies> getCompanies(Integer id) throws CompaniesInexistantException {
		
		ResultSet resultSet = null;
		Companies result = new Companies();
		if (id == null) {
			result.setId(null);
		}else {
			try {
				init();
				PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPANIES_ID + id);
				logger.debug("Requête : " + prep1.toString());
				resultSet = prep1.executeQuery();
				if (resultSet.next()) {
					result.setName(resultSet.getString(1));
					result.setId(id);
				}else {
					throw new CompaniesInexistantException();
				}
			} catch (SQLException e) {
				logger.warn(e.getMessage());				
			}finally {
				DB_Connection.getInstance().disconnect();
			}
		}
		return Optional.ofNullable(result);
	}

}