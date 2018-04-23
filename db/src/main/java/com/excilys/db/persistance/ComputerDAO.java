package com.excilys.db.persistance;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.excilys.db.mapper.RowMapperComputer;
import com.excilys.db.mapper.RowMapperInteger;
import com.excilys.db.model.Computer;
import com.excilys.db.page.PageComputerDTO;

import javax.sql.DataSource;

/**
 *
 * @author flotte
 */
@Repository("computerDAO")
public class ComputerDAO implements IComputerDAO {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private RowMapperComputer mapperComputer;
    @Autowired
    private RowMapperInteger mapperInteger;
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerDAO.class);

    private static final String QUERRY_LIST_COMPUTERS = "SELECT computer.name, computer.introduced, computer.discontinued, company.id, computer.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id";
    private static final String QUERRY_LIST_COMPUTERS_ID = "SELECT computer.name, introduced, discontinued, company.id, computer.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = %d ";
    private static final String QUERRY_UPDATE_COMPUTER = "UPDATE computer SET name = ? , introduced = ? , discontinued = ? , company_id = ? WHERE id = ? ";
    private static final String QUERRY_CREATE_COMPUTER = "INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (:name , :introduced , :discontinued , :companyId )";
    private static final String QUERRY_LIST_COMPUTER_BY_NAME = "SELECT computer.id FROM computer WHERE name = '%s' ";
    private static final String QUERRY_DELETE_COMPUTER = "DELETE FROM computer WHERE id = %d ";
    private static final String OFFSET_LIMIT = " LIMIT %d OFFSET %d";
    private static final String ORDER_BY = " ORDER BY %s %s ";
    private static final String QUERRY_COUNT = "SELECT COUNT(*) FROM computer LEFT JOIN company ON computer.company_id = company.id";
    private static final String LIKE = " WHERE computer.name LIKE \'%%%s%%\' or company.name LIKE \'%%%s%%\'";

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#listComputer()
     */
    @Override
    public List<Computer> listComputer() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        return vJdbcTemplate.query(QUERRY_LIST_COMPUTERS,mapperComputer);
    }


    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#listComputer(int, int, java.lang.String, java.lang.String)
     */
    @Override
    public List<Computer> listComputer(int offset, int limit,String sortBy, String orderBy ) {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        return vJdbcTemplate.query(QUERRY_LIST_COMPUTERS + String.format(ORDER_BY, sortBy, orderBy)  + String.format(OFFSET_LIMIT,limit,offset),mapperComputer);
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
        return listComputer(offset,limit,sortBy,orderBy);
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#showDetails(int)
     */
    @Override
    public Optional<Computer> showDetails(int id) {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        List<Computer> results = vJdbcTemplate.query(String.format(QUERRY_LIST_COMPUTERS_ID,id),mapperComputer);
        if (results == null) {
            return Optional.ofNullable(null);
        } else if (results.isEmpty()){
            return Optional.ofNullable(null);
        }else {
            return Optional.ofNullable(results.get(0));
        }

    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#updateAComputer(com.excilys.db.model.Computer, int)
     */
    @Override
    public void updateAComputer(Computer computer, int id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String query = QUERRY_UPDATE_COMPUTER;
        jdbcTemplate.update(query, computer.getName(),
                computer.getIntroduced() == null ? null : Date.valueOf(computer.getIntroduced()),
                        computer.getDiscontinued() == null ? null : Date.valueOf(computer.getDiscontinued()),
                                computer.getCompany() == null ? null : computer.getCompany().getId(),
                                        id);
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#createAComputer(com.excilys.db.model.Computer)
     */
    @Override
    public int createAComputer(Computer computer) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        String vSQL = QUERRY_CREATE_COMPUTER;
        MapSqlParameterSource vParams = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        vParams.addValue("companyId", computer.getCompany() == null ? null : computer.getCompany().getId());
        vParams.addValue("name", computer.getName());
        vParams.addValue("introduced", computer.getIntroduced() == null ? null : Date.valueOf(computer.getIntroduced()));
        vParams.addValue("discontinued", computer.getDiscontinued() == null ? null : Date.valueOf(computer.getDiscontinued()));
        vParams.addValue("id", computer.getId());
        vJdbcTemplate.update(vSQL, vParams, keyHolder);
        return keyHolder.getKey().intValue();
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#getIdFromName(java.lang.String)
     */
    @Override
    public List<Integer> getIdFromName(String name) {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        return vJdbcTemplate.query(String.format(QUERRY_LIST_COMPUTER_BY_NAME,name),mapperInteger);
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#deleteAComputer(int)
     */
    @Override
    public void deleteAComputer(int id) {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        vJdbcTemplate.update(String.format(QUERRY_DELETE_COMPUTER,id));
    }


    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#deleteListComputer(int[])
     */
    @Override
    public void deleteListComputer(List<Integer> ids) {
        for (int i=0;i < ids.size();i++) {
            deleteAComputer(ids.get(i));
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#getCount()
     */
    @Override
    public int getCount() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        return vJdbcTemplate.queryForObject(QUERRY_COUNT,Integer.class);
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#getCount(java.lang.String)
     */
    @Override
    public int getCount(String search) {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        return vJdbcTemplate.queryForObject(QUERRY_COUNT + String.format(LIKE, search,search),Integer.class);
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#listComputerLike(int, int, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<Computer> listComputerLike(int offset, int limit, String name, String sortBy, String orderBy) {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        return vJdbcTemplate.query(QUERRY_LIST_COMPUTERS + String.format(LIKE, name, name) + String.format(ORDER_BY, sortBy, orderBy) + String.format(OFFSET_LIMIT,limit,offset),mapperComputer);
    }

    @Override
    public List<Computer> listComputerLike(PageComputerDTO page) {
        int offset = (page.getPageNumber() - 1) * page.getPageSize();
        int limit = page.getPageSize();
        String name = page.getSearch();
        String sortBy = page.getSortBy();
        String orderBy = page.getOrderBy();
        return listComputerLike(offset, limit, name, sortBy, orderBy);
    }

}