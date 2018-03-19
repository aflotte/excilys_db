package com.excilys.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import com.excilys.db.exception.CompaniesInexistant;
import com.excilys.db.mapper.ComputerMapper;
import com.excilys.db.model.Companies;
import com.excilys.db.model.Computer;
import com.excilys.db.persistance.DB_Connection;
import java.sql.Statement;

/**
 * La classe DAO des ordinateurs
 * @author flotte
 *
 */
public class ComputerDAO {
	org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerDAO.class);

	private final static String QUERRY_LIST_COMPUTERS = "SELECT name, introduced, discontinued, company_id, id FROM computer";
	private final static String QUERRY_LIST_COMPUTERS_ID = "SELECT name, introduced, discontinued, company_id, id FROM computer WHERE id = ";
	private final static String QUERRY_UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private final static String QUERRY_CREATE_COMPUTER = "INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private final static String QUERRY_LIST_COMPUTER_BY_NAME = "SELECT id FROM computer WHERE name = ?";
	private final static String QUERRY_DELETE_COMPUTER = "DELETE FROM computer WHERE id = ";

	private String chooseTheQuerryCompanie(Companies comp) {
		if (comp.getId() == null) {
			return " AND company_id is ?";
		}else {
			return " AND company_id = ?";
		}
	}

	private String chooseTheQuerry(LocalDate localDate,LocalDate localDate2, Companies companies ) {
		String querryEnd = chooseTheQuerryCompanie(companies);
		String querryNotNULL = "SELECT id FROM computer WHERE name = ? AND introduced = ? AND discontinued = ?"+querryEnd;
		String querryIntroducedNULL = "SELECT id FROM computer WHERE name = ? AND introduced is ? AND discontinued = ?"+querryEnd;
		String querryDiscontinuedNULL = "SELECT id FROM computer WHERE name = ? AND introduced = ? AND discontinued is ?"+querryEnd;
		String querryBothNULL = "SELECT id FROM computer WHERE name = ? AND introduced is ? AND discontinued is ?"+querryEnd;
		if(localDate == null) {
			if(localDate2 == null) {
				return querryBothNULL;
			}else {
				return querryIntroducedNULL;
			}
		}else {
			if(localDate2 == null) {
				return querryDiscontinuedNULL;

			}else {
				return querryNotNULL;
			}
		}
	}

	private static ComputerDAO instance;
	private static Connection conn;

	public static ComputerDAO getInstance() {
		if (null == instance) {
			instance = new ComputerDAO();
		}
		return instance;

	}

	private void initialisationConnection() throws ClassNotFoundException {
		DB_Connection.getInstance().connect();
		conn = DB_Connection.getConn();

	}

	public List<Computer> listComputer() throws CompaniesInexistant {
		try {
			initialisationConnection();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		List<Computer> listResult = new ArrayList<Computer>();
		try {
			PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPUTERS);
			logger.debug("Requête : " + prep1.toString());
			ResultSet resultSet = prep1.executeQuery();
			while (resultSet.next()) {
				Computer toAdd = ComputerMapper.resultToComputer(resultSet);
				listResult.add(toAdd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally { 
			DB_Connection.getInstance().disconnect();
		}
		return listResult;
	}

	public Computer showDetails(int id) throws CompaniesInexistant {
		try {
			initialisationConnection();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		Computer result = new Computer();
		try {
			PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPUTERS_ID + id);
			logger.debug("Requête : " + prep1.toString());
			ResultSet resultSet = prep1.executeQuery();
			if (resultSet.next()) {
				result = ComputerMapper.resultToComputer(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DB_Connection.getInstance().disconnect();
		}
		return result;
	}

	public void updateAComputer(Computer computer, int id) {	
		try {
			initialisationConnection();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		LocalDate dateIntroduced = computer.getIntroduced();
		LocalDate dateDiscontinued = computer.getDiscontinued();
		try {
			PreparedStatement ps = conn.prepareStatement(QUERRY_UPDATE_COMPUTER);
			ps.setString(1, computer.getName());
			if (dateIntroduced == null) {
				ps.setNString(2,null);
			}else {
				ps.setDate(2, java.sql.Date.valueOf(dateIntroduced));
			}
			if (dateDiscontinued == null) {
				ps.setNString(3,null);
			}else {
				ps.setDate(3, java.sql.Date.valueOf(dateDiscontinued));
			}
			if ((computer.getCompanyId() != null)&&(computer.getCompanyId().getId() != null)) {

				ps.setInt(4, computer.getCompanyId().getId());
			}else {
				ps.setNull(4, java.sql.Types.INTEGER);
			}
			ps.setInt(5,id);
			logger.debug("Requête : " + ps.toString());
			ps.executeUpdate();
			DB_Connection.getInstance().disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
			DB_Connection.getInstance().disconnect();
		}
	}

	public int createAComputer(Computer computer){
		try {
			initialisationConnection();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		LocalDate dateIntroduced = computer.getIntroduced();
		LocalDate dateDiscontinued = computer.getDiscontinued();
		try {
			PreparedStatement ps = conn.prepareStatement(QUERRY_CREATE_COMPUTER,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, computer.getName());
			if (dateIntroduced == null) {
				ps.setNString(2,null);
			}else {
				ps.setDate(2, java.sql.Date.valueOf(dateIntroduced));
			}
			if (dateDiscontinued == null) {
				ps.setNString(3,null);
			}else {
				ps.setDate(3, java.sql.Date.valueOf(dateDiscontinued));
			}
			if (computer.getCompanyId().getId() != null) {
				ps.setInt(4, computer.getCompanyId().getId());
			}else {
				ps.setNull(4, java.sql.Types.INTEGER);
			}
			logger.debug("Requête : " + ps.toString());
			ps.executeUpdate();
			ResultSet key = ps.getGeneratedKeys();
			int ikey = 0;
			if (key.next()) {
				ikey = key.getInt(1);
			}
			DB_Connection.getInstance().disconnect();
			return ikey;

		} catch (SQLException e) {
			e.printStackTrace();
			DB_Connection.getInstance().disconnect();
		} catch (InputMismatchException e) {
			System.out.println("Entrez un entier pour le champ companie_Id!");
		}
		return 0;

	}

	private void fillGetIdStatement(PreparedStatement ps,Computer computer) throws SQLException {
		LocalDate dateIntroduced = computer.getIntroduced();
		LocalDate dateDiscontinued = computer.getDiscontinued();

		ps.setString(1, computer.getName());
		if (dateIntroduced == null) {
			ps.setNString(2,null);
		}else {
			ps.setDate(2, java.sql.Date.valueOf(dateIntroduced));
		}
		if (dateDiscontinued == null) {
			ps.setNString(3,null);
		}else {
			ps.setDate(3, java.sql.Date.valueOf(dateDiscontinued));
		}
		if (computer.getCompanyId().getId() != null) {
			ps.setInt(4, computer.getCompanyId().getId());
		}else {
			ps.setNull(4, java.sql.Types.INTEGER);
		}

	}

	public List<Integer> getId(Computer computer) {
		try {
			initialisationConnection();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		List<Integer> result = new ArrayList<Integer>();
		String querry = chooseTheQuerry(computer.getIntroduced(),computer.getDiscontinued(),computer.getCompanyId());
		try {
			PreparedStatement ps = conn.prepareStatement(querry);
			fillGetIdStatement(ps,computer);
			logger.debug("Requête : " + ps.toString());
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				result.add(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DB_Connection.getInstance().disconnect();
		}

		return result;

	}

	public List<Integer> getIdFromName(String name) {
		try {
			initialisationConnection();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		List<Integer> result = new ArrayList<Integer>();
		try {
			PreparedStatement ps = conn.prepareStatement(QUERRY_LIST_COMPUTER_BY_NAME);
			ps.setString(1, name);
			logger.debug("Requête : " + ps.toString());
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				result.add(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DB_Connection.getInstance().disconnect();
		}
		return result;

	}

	public void deleteAComputer(int id) {
		try {
			initialisationConnection();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			PreparedStatement prep1 = conn.prepareStatement(QUERRY_DELETE_COMPUTER + id);
			logger.debug("Requête : " + prep1.toString());
			prep1.executeUpdate(QUERRY_DELETE_COMPUTER + id);
		} catch (SQLException e) {
			e.printStackTrace();

		}finally {
			DB_Connection.getInstance().disconnect();
		}
	}

}