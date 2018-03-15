package com.excilys.db.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;

import com.excilys.db.exception.CompaniesInexistant;
import com.excilys.db.mapper.ComputerMapper;
import com.excilys.db.model.Companies;
import com.excilys.db.model.Computer;
import com.excilys.db.persistance.DB_Connection;
import com.mysql.jdbc.Statement;

/**
 * La classe DAO des ordinateurs
 * @author flotte
 *
 */
public class ComputerDAO {

	
	private static ComputerDAO instance;
	private static Connection conn;
    private ComputerDAO() {
    	
    	
    }
    
    public static ComputerDAO getInstance() {
		if (null == instance) {
			instance = new ComputerDAO();
		}
    	return instance;
    	
    }
	
    /**
     * L'initialisation de la connexion
     */
	private void init() {
		DB_Connection.getInstance().connect();
		conn = DB_Connection.getConn();
		
	}
	
	/**
	 * 
	 * @return la liste des ordinateurs
	 * @throws CompaniesInexistant 
	 */
	public List<Computer> listComputer() throws CompaniesInexistant {
		init();
		ResultSet resultSet = null;
		List<Computer> listResult = new ArrayList<Computer>();
		String querry = "SELECT name, introduced, discontinued, company_id, id FROM computer";
		try {
			PreparedStatement prep1 = conn.prepareStatement(querry);
			resultSet = prep1.executeQuery();
			while (resultSet.next()) {
				Computer toAdd = ComputerMapper.resultToComputer(resultSet);
				listResult.add(toAdd);
			}
			DB_Connection.getInstance().disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
			DB_Connection.getInstance().disconnect();
		}
		return listResult;
	}

	/**
	 * 
	 * @param id de l'ordinateur
	 * @return l'ordinateur
	 * @throws CompaniesInexistant 
	 */
	public Computer showDetails(int id) throws CompaniesInexistant {
		init();
		ResultSet resultSet = null;
		Computer result = new Computer();
		String querry = "SELECT name, introduced, discontinued, company_id, id FROM computer WHERE id = " + id;
		try {
			PreparedStatement prep1 = conn.prepareStatement(querry);
			resultSet = prep1.executeQuery();
			if (resultSet.next()) {
				result = ComputerMapper.resultToComputer(resultSet);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			DB_Connection.getInstance().disconnect();
		}
		DB_Connection.getInstance().disconnect();
		return result;
	}

	/**
	 * 
	 * @param computer l'ordinateur qui remplacera l'ancien
	 * @param id de l'ordinateur à remplacer
	 */
	public void updateAComputer(Computer computer, int id) {	
		init();
		LocalDate dateIntroduced = computer.getIntroduced();
		LocalDate dateDiscontinued = computer.getDiscontinued();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String querry = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(querry);
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
			if (computer.getCompanyId() != null) {
			ps.setInt(4, computer.getCompanyId().getId());
			}else {
				ps.setNull(4, java.sql.Types.INTEGER);
			}
			ps.setInt(5,id);
			ps.executeUpdate();
			DB_Connection.getInstance().disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
			DB_Connection.getInstance().disconnect();
		}
	}

	public int createAComputer(Computer computer){
		init();
		LocalDate dateIntroduced = computer.getIntroduced();
		LocalDate dateDiscontinued = computer.getDiscontinued();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String querry = "INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(querry,Statement.RETURN_GENERATED_KEYS);
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
			System.out.println("Entrez un entier our le champ companie_Id!");
		}
		return 0;
		
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
	
	private String chooseTheQuerryCompanie(Companies comp) {
		if (comp == null) {
			return " AND company_id is ?";
		}else {
			return " AND company_id = ?";
		}
	}
	
	private void fillGetIdStatement(PreparedStatement ps,Computer computer) throws SQLException {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
		init();
		List<Integer> result = new ArrayList<Integer>();
		ResultSet resultSet = null;
		String querry = chooseTheQuerry(computer.getIntroduced(),computer.getDiscontinued(),computer.getCompanyId());
		try {
			PreparedStatement ps = conn.prepareStatement(querry);
			fillGetIdStatement(ps,computer);
			System.out.println(ps);
			resultSet = ps.executeQuery();
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
		init();
		List<Integer> result = new ArrayList<Integer>();
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
			e.printStackTrace();
		}finally {
		DB_Connection.getInstance().disconnect();
		}
		return result;
	
	}
	
	

	public void deleteAComputer(int id) {
		init();
		String querry = "DELETE FROM computer WHERE id = "+ id;
		try {
			PreparedStatement prep1 = conn.prepareStatement(querry);
			prep1.executeUpdate(querry);
		} catch (SQLException e) {
			e.printStackTrace();
			
		}finally {
			DB_Connection.getInstance().disconnect();
		}
	}

}
