package com.excilys.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.mapper.CompaniesMapper;
import com.excilys.db.model.Company;
import com.excilys.db.persistance.DBConnection;

/**
 * La classe DAO de Companies.
 * @author flotte
 *
 */
public enum CompaniesDAO {
    INSTANCE;
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompaniesDAO.class);
    private static Connection conn;

    private static final String QUERRY_LIST_COMPANIES_BY_NAME = "SELECT id FROM company WHERE name = ?";
    private static final String QUERRY_LIST_COMPANIES = "SELECT name, id FROM company";
    private static final String QUERRY_LIST_COMPANIES_ID = "SELECT name FROM company WHERE id = ";
    private static final String OFFSET_LIMIT = " LIMIT ? OFFSET ?";
    private static final String QUERRY_COUNT = "SELECT COUNT(*) FROM company";

    /**
     * Initialise la connection.
     */
    private void initialisationConnection() {
        DBConnection.getInstance().connect();
        conn = DBConnection.getConn();

    }

    /**
     *
     * @param id d'une compagnie
     * @return true si la compagnie exist, false sinon
     */
    @SuppressWarnings("resource")
    public boolean existCompanies(int id) {
        initialisationConnection();
        ResultSet resultSet = null;
        try {
            PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPANIES_ID + id);
            logger.debug("Requête : " + prep1.toString());
            resultSet = prep1.executeQuery();
            boolean result = resultSet.next();
            resultSet.close();
            prep1.close();
            return result;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            DBConnection.getInstance().disconnect();
        }
        return false;
    }

    /**
     *
     * @return la liste des compagnies
     */
    @SuppressWarnings("resource")
    public List<Company> listCompanies() {
        initialisationConnection();
        ResultSet resultSet = null;
        List<Company> listResult = new ArrayList<Company>();
        try {
            PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPANIES);
            logger.debug("Requête : " + prep1.toString());
            resultSet = prep1.executeQuery();
            while (resultSet.next()) {
                Company toAdd = new Company();
                toAdd.setId(resultSet.getInt(2));
                toAdd.setName(resultSet.getString(1));
                listResult.add(toAdd);
            }
            prep1.close();
            resultSet.close();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            DBConnection.getInstance().disconnect();
        }
        return listResult;
    }


    /**
    *
    * @param offset l'offset
    * @param limit le nombre a afficher
    * @return la liste des compagnies
    * @throws CompaniesInexistantException erreur sur la compagnie de l'ordinateur
    */
   public List<Company> listComputer(int offset, int limit) throws CompaniesInexistantException {
       initialisationConnection();
       List<Company> listResult = new ArrayList<Company>();
       try {
           PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPANIES + OFFSET_LIMIT);
           prep1.setInt(1, limit);
           prep1.setInt(2, offset);
           logger.debug("Requête : " + prep1.toString());
           ResultSet resultSet = prep1.executeQuery();
           while (resultSet.next()) {
               Company toAdd = CompaniesMapper.computerResultToCompanies(resultSet);
               listResult.add(toAdd);
           }
           prep1.close();
           resultSet.close();
       } catch (SQLException e) {
           logger.warn(e.getMessage());
       } finally {
           DBConnection.getInstance().disconnect();
       }
       return listResult;
   }

    /**
     *
     * @param id de la compagnie
     * @return la compagnie
     * @throws CompaniesInexistantException la compagnie recherché n'existe pas
     */
    @SuppressWarnings("resource")
    public Optional<Company> getCompany(Integer id) throws CompaniesInexistantException {
        ResultSet resultSet = null;
        Company result = new Company();
        if (id == null) {
            result.setId(null);
        } else {
            try {
                initialisationConnection();
                PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPANIES_ID + id);
                logger.debug("Requête : " + prep1.toString());
                resultSet = prep1.executeQuery();
                if (resultSet.next()) {
                    result.setName(resultSet.getString(1));
                    result.setId(id);
                } else {
                    resultSet.close();
                    prep1.close();
                    throw new CompaniesInexistantException();
                }
                resultSet.close();
                prep1.close();
            } catch (SQLException e) {
                logger.warn(e.getMessage());
            } finally {
                DBConnection.getInstance().disconnect();
            }
        }
        return Optional.ofNullable(result);
    }
    
    
    /**
    *
    */
   public void getCount() {
       initialisationConnection();
       try {
           PreparedStatement prep1 = conn.prepareStatement(QUERRY_COUNT);
           logger.debug("Requête : " + prep1.toString());
           prep1.executeQuery();
           prep1.close();
       } catch (SQLException e) {
           logger.warn(e.getMessage());
       } finally {
           DBConnection.getInstance().disconnect();
       }
   }
   
   /**
   *
   * @param name le nom de l'ordinateur
   * @return la liste des Id
   */
  public List<Integer> getIdFromName(String name) {
      initialisationConnection();
      List<Integer> result = new ArrayList<Integer>();
      try {
          PreparedStatement ps = conn.prepareStatement(QUERRY_LIST_COMPANIES_BY_NAME);
          ps.setString(1, name);
          logger.debug("Requête : " + ps.toString());
          ResultSet resultSet = ps.executeQuery();
          while (resultSet.next()) {
              result.add(new Integer(resultSet.getInt(1)));
          }
          resultSet.close();
          ps.close();
      } catch (SQLException e) {
          logger.warn(e.getMessage());
      } finally {
          DBConnection.getInstance().disconnect();
      }
      return result;
  }
}