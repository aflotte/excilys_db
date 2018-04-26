package com.excilys.db.persistance;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.db.mapper.RowMapperCompany;
import com.excilys.db.mapper.RowMapperInteger;
import com.excilys.db.mapper.RowMapperIntegerFromCompany;
import com.excilys.db.model.Company;

/**
 * La classe DAO de Companies.
 * @author flotte
 *
 */
@Repository("companiesDAO")
public class CompaniesDAO implements ICompaniesDAO {
    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    private DataSource dataSource;
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompaniesDAO.class);
    @Autowired
    private IComputerDAO computerDAO;
    @Autowired
    private RowMapperCompany mapperCompany;
    @Autowired
    private RowMapperIntegerFromCompany mapperIntegerFromCompany;
    @Autowired
    private RowMapperInteger mapperInteger;


    private static final String QUERRY_LIST_COMPANIES_BY_NAME = "SELECT company.id FROM " + Company.class.getName() + " WHERE name LIKE \'%s\'";
    private static final String QUERRY_LIST_COMPANIES = "FROM " + Company.class.getName();
    private static final String QUERRY_LIST_COMPANIES_ID = "FROM " + Company.class.getName() + "WHERE id = %d";
    private static final String QUERRY_LIST_COMPUTER = "SELECT computer.id FROM computer WHERE computer.company_id = %d ";
    private static final String OFFSET_LIMIT = " LIMIT %d OFFSET %d";
    private static final String DELETE_COMPANY = "DELETE FROM company WHERE id = %d";
    private static final String QUERRY_COUNT = "SELECT COUNT(*) FROM company";



    /* (non-Javadoc)
     * @see com.excilys.db.persistance.ICompaniesDAO#computerFromCompany(int)
     */
    @Override
    public List<Integer> computerFromCompany(int id){
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        return vJdbcTemplate.query(String.format(QUERRY_LIST_COMPUTER,id),mapperInteger);
    }


    /* (non-Javadoc)
     * @see com.excilys.db.persistance.ICompaniesDAO#existCompanies(int)
     */
    @Override
    public boolean existCompanies(int id) {
        try (Session session = sessionFactory.openSession();){
            TypedQuery<Company> querry = session.createQuery(String.format(QUERRY_LIST_COMPANIES_ID,id),Company.class);
            return !querry.getResultList().isEmpty();
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.ICompaniesDAO#listCompanies()
     */
    @Override
    public List<Company> listCompanies() {
        try(Session session = sessionFactory.openSession();){
            TypedQuery<Company> querry = session.createQuery(QUERRY_LIST_COMPANIES,Company.class);

            return querry.getResultList();
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.ICompaniesDAO#deleteCompany(int)
     */
    @Override
    public void deleteCompany(int id) {
        List<Integer> computerIds = computerFromCompany(id);
        computerDAO.deleteListComputer(computerIds);
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        vJdbcTemplate.update(String.format(DELETE_COMPANY,id));
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.ICompaniesDAO#listCompany(int, int)
     */
    @Override
    public List<Company> listCompany(int offset, int limit) {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        return vJdbcTemplate.query(QUERRY_LIST_COMPANIES + String.format(OFFSET_LIMIT,limit,offset), mapperCompany);
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.ICompaniesDAO#getCompany(java.lang.Integer)
     */
    @Override
    public Optional<Company> getCompany(Integer id) {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        List<Company> results = vJdbcTemplate.query(String.format(QUERRY_LIST_COMPANIES_ID,id), mapperCompany);
        if (results == null) {
            return Optional.ofNullable(null);
        } else if (results.isEmpty()){
            return Optional.ofNullable(null);
        }else {
            return Optional.ofNullable(results.get(0));
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.ICompaniesDAO#getCount()
     */
    @Override
    public int getCount() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        return vJdbcTemplate.queryForObject(QUERRY_COUNT,Integer.class);
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.ICompaniesDAO#getIdFromName(java.lang.String)
     */
    @Override
    public List<Integer> getIdFromName(String name) {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        return vJdbcTemplate.query(String.format(QUERRY_LIST_COMPANIES_BY_NAME, name), mapperIntegerFromCompany);
    }

}