package com.excilys.db.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.excilys.db.exception.DAOAccesExeption;
import com.excilys.db.mapper.ComputerMapper;
import com.excilys.db.model.Computer;
import com.excilys.db.page.PageComputerDTO;

import java.sql.Statement;
import com.excilys.db.utils.Debugging;

/**
 *
 * @author flotte
 */
@Repository("computerDAO")
public class ComputerDAO implements IComputerDAO {
    @Autowired
    private DataSource dataSource;
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerDAO.class);
    private static final String ERROR = "Erreur dans l'accès des données";

    private static final String QUERRY_LIST_COMPUTERS = "SELECT computer.name, computer.introduced, computer.discontinued, company.id, computer.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id";
    private static final String QUERRY_LIST_COMPUTERS_ID = "SELECT computer.name, introduced, discontinued, company.id, computer.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ? ";
    private static final String QUERRY_UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ? ";
    private static final String QUERRY_CREATE_COMPUTER = "INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
    private static final String QUERRY_LIST_COMPUTER_BY_NAME = "SELECT id FROM computer WHERE name = ? ";
    private static final String QUERRY_DELETE_COMPUTER = "DELETE FROM computer WHERE id = ? ";
    private static final String OFFSET_LIMIT = " LIMIT ? OFFSET ?";
    private static final String ORDER_BY = " ORDER BY %s %s ";
    private static final String QUERRY_COUNT = "SELECT COUNT(*) FROM computer LEFT JOIN company ON computer.company_id = company.id";
    private static final String LIKE = " WHERE computer.name LIKE \'%%%s%%\' or company.name LIKE \'%%%s%%\'";

    /**
     *
     * @param prep1 le prepared statement
     * @return la liste d'ordinateur
     * @throws SQLException l'erreur
     */
    private List<Computer> requestToListComputer(PreparedStatement prep1) throws SQLException {
        List<Computer> listResult = new ArrayList<>();
        Debugging.requestDebug(logger, prep1.toString());
        try (ResultSet resultSet = prep1.executeQuery();){
            while (resultSet.next()) {
                Computer toAdd = ComputerMapper.resultToComputer(resultSet);
                listResult.add(toAdd);
            }
        }
        return listResult;
    }

    /**
     *
     * @param prep1 le prepapred statement
     * @return l'ordinateur
     * @throws SQLException l'erreur
     */
    private Optional<Computer> requestToComputer(PreparedStatement prep1) throws SQLException{
        Debugging.requestDebug(logger, prep1.toString());
        Computer result = null;
        try (ResultSet resultSet = prep1.executeQuery();){
            if (resultSet.next()) {
                result = ComputerMapper.resultToComputer(resultSet);
            }
        }

        return Optional.ofNullable(result);
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#listComputer()
     */
    @Override
    public List<Computer> listComputer() {
        List<Computer> listResult;
        try (Connection conn = DBConnection.getConn();PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPUTERS);){
            
            listResult = requestToListComputer(prep1);
        } catch (SQLException e) {
            logger.error(ERROR);
            throw new DAOAccesExeption();
        }
        return listResult;
    }


    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#listComputer(int, int, java.lang.String, java.lang.String)
     */
    @Override
    public List<Computer> listComputer(int offset, int limit,String sortBy, String orderBy ) {
        List<Computer> listResult;
        try (Connection conn = DBConnection.getConn();PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPUTERS + String.format(ORDER_BY, sortBy, orderBy)  + OFFSET_LIMIT );){
            prep1.setInt(1, limit);
            prep1.setInt(2, offset);
            listResult = requestToListComputer(prep1);
        } catch (SQLException e) {
            logger.error(ERROR);
            throw new DAOAccesExeption();
        }
        return listResult;
    }
    
    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#listComputer(com.excilys.db.page.PageComputerDTO)
     */
   @Override
public List<Computer> listComputer(PageComputerDTO page) {
       int offset = (page.getPageNumber() - 1) * page.getPageSize();
       int limit = page.getPageSize();
       String sortBy = page.getSortBy();
       String orderBy = page.getOrderBy();
       List<Computer> listResult;
       try (Connection conn = DBConnection.getConn();PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPUTERS + String.format(ORDER_BY, sortBy, orderBy)  + OFFSET_LIMIT );){
           prep1.setInt(1, limit);
           prep1.setInt(2, offset);
           listResult = requestToListComputer(prep1);
       } catch (SQLException e) {
           logger.error(ERROR);
           throw new DAOAccesExeption();
       }
       return listResult;
   }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#showDetails(int)
     */
    @Override
    public Optional<Computer> showDetails(int id) {
        Optional<Computer> result = null;
        try (Connection conn = DBConnection.getConn();PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPUTERS_ID);){
            prep1.setInt(1, id);
            result = requestToComputer(prep1);

        } catch (SQLException e) {
            logger.error(ERROR);
            throw new DAOAccesExeption();
        }
        return result;
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#updateAComputer(com.excilys.db.model.Computer, int)
     */
    @Override
    public void updateAComputer(Computer computer, int id) {
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
            Debugging.requestDebug(logger, ps.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error(ERROR);
            throw new DAOAccesExeption();
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#createAComputer(com.excilys.db.model.Computer)
     */
    @Override
    public int createAComputer(Computer computer) {

        try (Connection conn = DBConnection.getConn();PreparedStatement ps = conn.prepareStatement(QUERRY_CREATE_COMPUTER, Statement.RETURN_GENERATED_KEYS);){
            fillGetIdStatement(ps, computer);
            Debugging.requestDebug(logger, ps.toString());
            ps.executeUpdate();
            try (ResultSet key = ps.getGeneratedKeys();){
                int ikey = 0;
                if (key.next()) {
                    ikey = key.getInt(1);
                }
                return ikey;
            }

        } catch (SQLException e) {
            logger.error(ERROR);
            throw new DAOAccesExeption();
        }
    }

    /**
     *
     * @param ps le PreparedStatement a remplir
     * @param computer l'ordinateur
     * @throws SQLException une erreur lors du dialogue avec la base de donnée
     */
    private void fillGetIdStatement(PreparedStatement ps, Computer computer) {
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
            logger.error(ERROR);
            throw new DAOAccesExeption();
        }

    }


    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#getIdFromName(java.lang.String)
     */
    @Override
    public List<Integer> getIdFromName(String name) {
        List<Integer> result = new ArrayList<>();
        try (Connection conn = DBConnection.getConn();PreparedStatement ps = conn.prepareStatement(QUERRY_LIST_COMPUTER_BY_NAME);){
            ps.setString(1, name);
            Debugging.requestDebug(logger, ps.toString());
            try (ResultSet resultSet = ps.executeQuery();){
                while (resultSet.next()) {
                    result.add(Integer.valueOf(resultSet.getInt(1)));
                }
            }
        } catch (SQLException e) {
            logger.error(ERROR);
            throw new DAOAccesExeption();
        }
        return result;
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#deleteAComputer(int)
     */
    @Override
    public void deleteAComputer(int id) {
        try (Connection conn = DBConnection.getConn();PreparedStatement prep1 = conn.prepareStatement(QUERRY_DELETE_COMPUTER);){
            prep1.setInt(1, id);
            Debugging.requestDebug(logger, prep1.toString());
            prep1.executeUpdate();
        } catch (SQLException e) {
            logger.error(ERROR);
            throw new DAOAccesExeption();
        }
    }


    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#deleteListComputer(int[])
     */
    @Override
    public void deleteListComputer(int[] ids) {
        List<Integer> listId = new ArrayList<>();
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
            throw new DAOAccesExeption();
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#deleteListComputer(java.sql.Connection, java.util.List)
     */
    @Override
    public void deleteListComputer(Connection conn, List<Integer> ids) throws SQLException {
        for (int i = 0; i < ids.size(); i++) {
            int id = ids.get(i);
            try (PreparedStatement prep1 = conn.prepareStatement(QUERRY_DELETE_COMPUTER);){
                prep1.setInt(1, id);
                Debugging.requestDebug(logger, prep1.toString());
                prep1.executeUpdate();
            }
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#getCount()
     */
    @Override
    public int getCount() {
        int result = 0;
        try (Connection conn = DBConnection.getConn();PreparedStatement prep1 = conn.prepareStatement(QUERRY_COUNT);){

            Debugging.requestDebug(logger, prep1.toString());
            try (ResultSet resultSet = prep1.executeQuery();){
                if (resultSet.next()) {
                    result = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            logger.error(ERROR);
            throw new DAOAccesExeption();
        }
        return result;
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#getCount(java.lang.String)
     */
    @Override
    public int getCount(String search) {
        int result = 0;
        try (Connection conn = DBConnection.getConn();PreparedStatement prep1 = conn.prepareStatement(QUERRY_COUNT + String.format(LIKE, search,search));){
            Debugging.requestDebug(logger, prep1.toString());
            try (ResultSet resultSet = prep1.executeQuery();){
                if (resultSet.next()) {
                    result = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            logger.error(ERROR);
            throw new DAOAccesExeption();
        }
        return result;
    }


    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#listComputerLike(int, int, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<Computer> listComputerLike(int offset, int limit, String name, String sortBy, String orderBy) {
        List<Computer> listResult = new ArrayList<>();
        try (Connection conn = DBConnection.getConn(); PreparedStatement prep1 = conn.prepareStatement(QUERRY_LIST_COMPUTERS + String.format(LIKE, name, name) + String.format(ORDER_BY, sortBy, orderBy) + OFFSET_LIMIT);) {
            prep1.setInt(1, limit);
            prep1.setInt(2, offset);
            Debugging.requestDebug(logger, prep1.toString());
            try (ResultSet resultSet = prep1.executeQuery();) {
                while (resultSet.next()) {
                    Computer toAdd = ComputerMapper.resultToComputer(resultSet);
                    listResult.add(toAdd);
                }
            }
        } catch (SQLException e) {
            logger.error(ERROR);
            throw new DAOAccesExeption();
        }
        return listResult;
    }

}