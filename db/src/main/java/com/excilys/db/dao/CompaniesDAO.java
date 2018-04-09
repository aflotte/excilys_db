package com.excilys.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.exception.DAOAccesExeption;
import com.excilys.db.mapper.CompaniesMapper;
import com.excilys.db.model.Company;
import com.excilys.db.persistance.DBConnection;
import com.excilys.db.utils.Close;
import com.excilys.db.utils.Debugging;

/**
 * La classe DAO de Companies.
 * @author flotte
 *
 */
public enum CompaniesDAO {
    INSTANCE;
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompaniesDAO.class);

    private static final String QUERRY_LIST_COMPANIES_BY_NAME = "SELECT id FROM company WHERE name LIKE ?";
    private static final String QUERRY_LIST_COMPANIES = "SELECT name, id FROM company";
    private static final String QUERRY_LIST_COMPANIES_ID = "SELECT name FROM company WHERE id = ?";
    private static final String QUERRY_LIST_COMPUTER = "SELECT computer.id FROM computer RIGHT JOIN company ON computer.company_id = ?";
    private static final String OFFSET_LIMIT = " LIMIT ? OFFSET ?";
    private static final String DELETE_COMPANY = "DELETE FROM company WHERE id = ?";
    private static final String QUERRY_COUNT = "SELECT COUNT(*) FROM company";




    public List<Integer> computerFromCompany(int id){
        List<Integer> result = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection conn = DBConnection.getConn();PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPUTER);){
            prep1.setInt(1, id);
            Debugging.requestDebug(logger, prep1.toString());
            resultSet = prep1.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getInt(1));
            }

            return result;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            Close.closeQuietly(resultSet);
        }
        return result;
    }


    /**
     *
     * @param id d'une compagnie
     * @return true si la compagnie exist, false sinon
     */
    public boolean existCompanies(int id) {
        ResultSet resultSet = null;
        try (Connection conn = DBConnection.getConn();PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPANIES_ID);){
            prep1.setInt(1, id);
            Debugging.requestDebug(logger, prep1.toString());
            resultSet = prep1.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            Close.closeQuietly(resultSet);
        }
        return false;
    }

    /**
     *
     * @return la liste des compagnies
     */
    public List<Company> listCompanies() {
        ResultSet resultSet = null;
        List<Company> listResult = new ArrayList<>();
        try (Connection conn = DBConnection.getConn();PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPANIES);){
            Debugging.requestDebug(logger, prep1.toString());
            resultSet = prep1.executeQuery();
            while (resultSet.next()) {
                Company toAdd = new Company();
                toAdd.setId(resultSet.getInt(2));
                toAdd.setName(resultSet.getString(1));
                listResult.add(toAdd);
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            Close.closeQuietly(resultSet);
        }
        return listResult;
    }


    public void deleteCompany(int id) {
        List<Integer> computerIds = computerFromCompany(id);
        try(   Connection conn = DBConnection.getConn();
                AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
                AutoRollback tm = new AutoRollback(conn);
                PreparedStatement prep1 = conn.prepareStatement(DELETE_COMPANY);)
        {
            ComputerDAO.INSTANCE.deleteListComputer(conn, computerIds);

            prep1.setInt(1, id);
            prep1.executeUpdate();
            tm.commit();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    /**
     *
     * @param offset l'offset
     * @param limit le nombre a afficher
     * @return la liste des compagnies
     * @throws CompaniesInexistantException erreur sur la compagnie de l'ordinateur
     */
    public List<Company> listComputer(int offset, int limit) {
        List<Company> listResult = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection conn = DBConnection.getConn();PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPANIES + OFFSET_LIMIT);){

            prep1.setInt(1, limit);
            prep1.setInt(2, offset);
            Debugging.requestDebug(logger,prep1.toString());
                resultSet = prep1.executeQuery();
            while (resultSet.next()) {
                Company toAdd = CompaniesMapper.computerResultToCompanies(resultSet);
                listResult.add(toAdd);
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            Close.closeQuietly(resultSet);
        }
        return listResult;
    }

    /**
     *
     * @param id de la compagnie
     * @return la compagnie
     * @throws CompaniesInexistantException la compagnie recherché n'existe pas
     */
    public Optional<Company> getCompany(Integer id) throws CompaniesInexistantException {
        ResultSet resultSet = null;
        Company result = new Company();
        if (id == null) {
            result.setId(null);
        } else {
            try (Connection conn = DBConnection.getConn();PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPANIES_ID);){
                prep1.setInt(1, id);
                Debugging.requestDebug(logger, prep1.toString());
                resultSet = prep1.executeQuery();
                if (resultSet.next()) {
                    result.setName(resultSet.getString(1));
                    result.setId(id);
                } else {
                    resultSet.close();
                    throw new CompaniesInexistantException();
                }
            } catch (SQLException e) {
                logger.warn(e.getMessage());
            } finally {
                Close.closeQuietly(resultSet);
            }
        }
        return Optional.ofNullable(result);
    }


    /**
     *
     */
    public void getCount() {
        try (Connection conn = DBConnection.getConn();PreparedStatement prep1 = conn.prepareStatement(QUERRY_COUNT);){
            Debugging.requestDebug(logger, prep1.toString());
            prep1.executeQuery();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    /**
     *
     * @param name le nom de l'ordinateur
     * @return la liste des Id
     */
    public List<Integer> getIdFromName(String name) {
        List<Integer> result = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection conn = DBConnection.getConn();PreparedStatement ps = conn.prepareStatement(QUERRY_LIST_COMPANIES_BY_NAME);){

            ps.setString(1, name);
            Debugging.requestDebug(logger, ps.toString());
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result.add(Integer.valueOf(resultSet.getInt(1)));
            }
        } catch (SQLException e) {
            String error = "Erreur dans l'accès des données";
            logger.error(error);
            throw new DAOAccesExeption();
        } finally {
            Close.closeQuietly(resultSet);
        }
        return result;
    }

}