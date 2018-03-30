package com.excilys.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.exception.DAOAccesExeption;
import com.excilys.db.mapper.ComputerMapper;
import com.excilys.db.model.Company;
import com.excilys.db.model.Computer;
import com.excilys.db.persistance.DBConnection;
import java.sql.Statement;

/**
 *
 * @author flotte
 */
public enum ComputerDAO {
    INSTANCE;
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerDAO.class);

    private static final String QUERRY_LIST_COMPUTERS = "SELECT computer.name, computer.introduced, computer.discontinued, company.id, computer.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id";
    private static final String QUERRY_LIST_COMPUTERS_ID = "SELECT computer.name, introduced, discontinued, company.id, computer.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ";
    private static final String QUERRY_UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
    private static final String QUERRY_CREATE_COMPUTER = "INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
    private static final String QUERRY_LIST_COMPUTER_BY_NAME = "SELECT id FROM computer WHERE name = ?";
    private static final String QUERRY_DELETE_COMPUTER = "DELETE FROM computer WHERE id = ";
    private static final String OFFSET_LIMIT = " LIMIT ? OFFSET ?";
    private static final String ORDER_BY = " ORDER BY ";
    private static final String QUERRY_COUNT = "SELECT COUNT(*) FROM computer";

    /**
     *
     * @param comp la compagnie
     * @return la fin de la querry
     */
    private String chooseTheQuerryCompanie(Company comp) {
        if (comp.getId() == null) {
            return " AND company_id is ?";
        }
        return " AND company_id = ?";
    }

    /**
     *
     * @param localDate date de début
     * @param localDate2 date de fin
     * @param companies la compagnie
     * @return la bonne querry
     */
    private String chooseTheQuerry(LocalDate localDate, LocalDate localDate2, Company companies) {
        String querryEnd = chooseTheQuerryCompanie(companies);
        String querryNotNULL = "SELECT id FROM computer WHERE name = ? AND introduced = ? AND discontinued = ?" + querryEnd;
        String querryIntroducedNULL = "SELECT id FROM computer WHERE name = ? AND introduced is ? AND discontinued = ?" + querryEnd;
        String querryDiscontinuedNULL = "SELECT id FROM computer WHERE name = ? AND introduced = ? AND discontinued is ?" + querryEnd;
        String querryBothNULL = "SELECT id FROM computer WHERE name = ? AND introduced is ? AND discontinued is ?" + querryEnd;
        if (localDate == null) {
            if (localDate2 == null) {
                return querryBothNULL;
            }
            return querryIntroducedNULL;
        }
        if (localDate2 == null) {
            return querryDiscontinuedNULL;
        }
        return querryNotNULL;
    }


    /**
     *
     * @return la liste des ordinateurs
     * @throws CompaniesInexistantException erreur sur la compagnie de l'ordinateur
     */
    public List<Computer> listComputer() throws DAOAccesExeption {
        List<Computer> listResult = new ArrayList<Computer>();
        try (Connection conn = DBConnection.getConn();PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPUTERS);){

            logger.debug("Requête : " + prep1.toString());
            ResultSet resultSet = prep1.executeQuery();
            while (resultSet.next()) {
                Computer toAdd = ComputerMapper.resultToComputer(resultSet);
                listResult.add(toAdd);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Erreur dans l'accès des données");
            throw new DAOAccesExeption();
        }
        return listResult;
    }


    /**
     *
     * @param offset l'offset
     * @param limit le nombre a afficher
     * @return la liste des ordinateurs
     * @throws CompaniesInexistantException erreur sur la compagnie de l'ordinateur
     */
    public List<Computer> listComputer(int offset, int limit,String sortBy, String orderBy ) throws DAOAccesExeption {
        List<Computer> listResult = new ArrayList<Computer>();
        try (Connection conn = DBConnection.getConn();PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPUTERS + ORDER_BY + sortBy + " " + orderBy  + OFFSET_LIMIT );){
            
            prep1.setInt(1, limit);
            prep1.setInt(2, offset);
            logger.debug("Requête : " + prep1.toString());
            ResultSet resultSet = prep1.executeQuery();
            while (resultSet.next()) {
                Computer toAdd = ComputerMapper.resultToComputer(resultSet);
                listResult.add(toAdd);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Erreur dans l'accès des données");
            throw new DAOAccesExeption();
        }
        return listResult;
    }

    /**
     *
     * @param id de l'ordinateur
     * @return un ordinateur
     * @throws CompaniesInexistantException si la compagnie de l'ordinateur est inexistante
     */
    public Optional<Computer> showDetails(int id) throws CompaniesInexistantException, DAOAccesExeption {
        Computer result = null;
        try (Connection conn = DBConnection.getConn();PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPUTERS_ID + id);){
            logger.debug("Requête : " + prep1.toString());
            ResultSet resultSet = prep1.executeQuery();
            if (resultSet.next()) {
                result = ComputerMapper.resultToComputer(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Erreur dans l'accès des données");
            throw new DAOAccesExeption();
        }
        return Optional.of(result);
    }

    /**
     *
     * @param computer l'ordinateur qui remplacera l'ancien
     * @param id de l'ordinateur a remplacer
     */
    public void updateAComputer(Computer computer, int id) throws DAOAccesExeption {
        LocalDate dateIntroduced = computer.getIntroduced();
        LocalDate dateDiscontinued = computer.getDiscontinued();
        try (Connection conn = DBConnection.getConn();PreparedStatement ps = conn.prepareStatement(QUERRY_UPDATE_COMPUTER);){
            ps.setString(1, computer.getName());
            if (dateIntroduced == null) {
                ps.setNString(2, null);
            } else {
                ps.setDate(2, java.sql.Date.valueOf(dateIntroduced));
            }
            if (dateDiscontinued == null) {
                ps.setNString(3, null);
            } else {
                ps.setDate(3, java.sql.Date.valueOf(dateDiscontinued));
            }
            if ((computer.getCompany() != null) && (computer.getCompany().getId() != null)) {

                ps.setInt(4, computer.getCompany().getId().intValue());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            ps.setInt(5, id);
            logger.debug("Requête : " + ps.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Erreur dans l'accès des données");
            throw new DAOAccesExeption();
        }
    }

    /**
     *
     * @param computer l'ordinateur a ajouter
     * @return l'Id de l'ordinateur qui vient d'être ajouter
     */
    public int createAComputer(Computer computer) throws DAOAccesExeption {
        LocalDate dateIntroduced = computer.getIntroduced();
        LocalDate dateDiscontinued = computer.getDiscontinued();
        try (Connection conn = DBConnection.getConn();PreparedStatement ps = conn.prepareStatement(QUERRY_CREATE_COMPUTER, Statement.RETURN_GENERATED_KEYS);){
            ps.setString(1, computer.getName());
            if (dateIntroduced == null) {
                ps.setNString(2, null);
            } else {
                ps.setDate(2, java.sql.Date.valueOf(dateIntroduced));
            }
            if (dateDiscontinued == null) {
                ps.setNString(3, null);
            } else {
                ps.setDate(3, java.sql.Date.valueOf(dateDiscontinued));
            }
            if (computer.getCompany().getId() != null) {
                ps.setInt(4, computer.getCompany().getId().intValue());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            logger.debug("Requête : " + ps.toString());
            ps.executeUpdate();
            ResultSet key = ps.getGeneratedKeys();
            int ikey = 0;
            if (key.next()) {
                ikey = key.getInt(1);
            }
            key.close();
            return ikey;

        } catch (SQLException e) {
            logger.error("Erreur dans l'accès des données");
            throw new DAOAccesExeption();
        }
    }

    /**
     *
     * @param ps le PreparedStatement a remplir
     * @param computer l'ordinateur
     * @throws SQLException une erreur lors du dialogue avec la base de donnée
     */
    private void fillGetIdStatement(PreparedStatement ps, Computer computer) throws DAOAccesExeption {
        LocalDate dateIntroduced = computer.getIntroduced();
        LocalDate dateDiscontinued = computer.getDiscontinued();
        try {
            ps.setString(1, computer.getName());
            if (dateIntroduced == null) {
                ps.setNString(2, null);
            } else {
                ps.setDate(2, java.sql.Date.valueOf(dateIntroduced));
            }
            if (dateDiscontinued == null) {
                ps.setNString(3, null);
            } else {
                ps.setDate(3, java.sql.Date.valueOf(dateDiscontinued));
            }
            if (computer.getCompany().getId() != null) {
                ps.setInt(4, computer.getCompany().getId().intValue());
            } else {

                ps.setNull(4, java.sql.Types.INTEGER);
            }
        } catch (SQLException e) {
            logger.error("Erreur dans l'accès des données");
            throw new DAOAccesExeption();
        }

    }

    /**
     *
     * @param computer l'ordinateur dont on veut l'Id
     * @return l'Id
     */
    public List<Integer> getId(Computer computer) throws DAOAccesExeption {
        List<Integer> result = new ArrayList<Integer>();
        String querry = chooseTheQuerry(computer.getIntroduced(), computer.getDiscontinued(), computer.getCompany());
        try (Connection conn = DBConnection.getConn();PreparedStatement ps = conn.prepareStatement(querry);){
            fillGetIdStatement(ps, computer);
            logger.debug("Requête : " + ps.toString());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result.add(new Integer(resultSet.getInt(1)));
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Erreur dans l'accès des données");
            throw new DAOAccesExeption();
        }
        return result;

    }

    /**
     *
     * @param name le nom de l'ordinateur
     * @return la liste des Id
     */
    public List<Integer> getIdFromName(String name) throws DAOAccesExeption {
        List<Integer> result = new ArrayList<Integer>();
        try (Connection conn = DBConnection.getConn();PreparedStatement ps = conn.prepareStatement(QUERRY_LIST_COMPUTER_BY_NAME);){
            ps.setString(1, name);
            logger.debug("Requête : " + ps.toString());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result.add(new Integer(resultSet.getInt(1)));
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Erreur dans l'accès des données");
            throw new DAOAccesExeption();
        }
        return result;
    }

    /**
     *
     * @param id de l'ordinateur a supprimer
     */
    public void deleteAComputer(int id) throws DAOAccesExeption {
        try (Connection conn = DBConnection.getConn();PreparedStatement prep1 = conn.prepareStatement(QUERRY_DELETE_COMPUTER + id);){
            logger.debug("Requête : " + prep1.toString());
            prep1.executeUpdate(QUERRY_DELETE_COMPUTER + id);
        } catch (SQLException e) {
            logger.error("Erreur dans l'accès des données");
            throw new DAOAccesExeption();
        }
    }


    public void deleteListComputer(int[] ids) {
        List<Integer> listId = new ArrayList<Integer>();
        for (int i=0;i < ids.length;i++) {
            listId.add(ids[i]);
        }
        try(   Connection conn = DBConnection.getConn();
                AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
                AutoRollback tm = new AutoRollback(conn)) 
        {
            deleteListComputer(conn, listId);
            tm.commit();
        }catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    public void deleteListComputer(Connection conn, List<Integer> ids) throws SQLException {
        for (int i = 0; i < ids.size(); i++) {
            int id = ids.get(i);
            PreparedStatement prep1 = conn.prepareStatement(QUERRY_DELETE_COMPUTER + id);
            logger.debug("Requête : " + prep1.toString());
            prep1.executeUpdate();
        }
    }
    
    /**
     *
     */
    public int getCount() throws DAOAccesExeption {
        int result = 0;
        try (Connection conn = DBConnection.getConn();PreparedStatement prep1 = conn.prepareStatement(QUERRY_COUNT);){

            logger.debug("Requête : " + prep1.toString());
            ResultSet resultSet = prep1.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Erreur dans l'accès des données");
            throw new DAOAccesExeption();
        }
        return result;
    }

    /**
     *
     */
    public int getCount(String search) throws DAOAccesExeption {
        int result = 0;
        try (Connection conn = DBConnection.getConn();){
            PreparedStatement prep1 = conn.prepareStatement(QUERRY_COUNT + " WHERE computer.name LIKE '%" + search + "%'");
            logger.debug("Requête : " + prep1.toString());
            ResultSet resultSet = prep1.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
            prep1.close();
        } catch (SQLException e) {
            logger.error("Erreur dans l'accès des données");
            throw new DAOAccesExeption();
        }
        return result;
    }


    public List<Computer> listComputerLike(int offset, int limit, String name, String sortBy, String orderBy) {
        List<Computer> listResult = new ArrayList<Computer>();
        try (Connection conn = DBConnection.getConn();PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPUTERS + " WHERE computer.name LIKE '%"+name +"%' " + ORDER_BY + sortBy + " " + orderBy + OFFSET_LIMIT );){
            prep1.setInt(1, limit);
            prep1.setInt(2, offset);
            logger.debug("Requête : " + prep1.toString());
            ResultSet resultSet = prep1.executeQuery();
            while (resultSet.next()) {
                Computer toAdd = ComputerMapper.resultToComputer(resultSet);
                listResult.add(toAdd);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Erreur dans l'accès des données");
            throw new DAOAccesExeption();
        }
        return listResult;
    }

}