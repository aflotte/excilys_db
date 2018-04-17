package com.excilys.db.persistance;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.mapper.RowMapperCompany;
import com.excilys.db.mapper.RowMapperInteger;
import com.excilys.db.model.Company;

/**
 * La classe DAO de Companies.
 * @author flotte
 *
 */
@Repository("companiesDAO")
public class CompaniesDAO {
    @Autowired
    private DataSource dataSource;
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompaniesDAO.class);
    @Autowired
    private IComputerDAO computerDAO;
    @Autowired
    private RowMapperCompany mapperCompany;
    @Autowired
    private RowMapperInteger mapperInteger;

    private static final String QUERRY_LIST_COMPANIES_BY_NAME = "SELECT company.id FROM company WHERE name LIKE %s";
    private static final String QUERRY_LIST_COMPANIES = "SELECT company.name, company.id FROM company";
    private static final String QUERRY_LIST_COMPANIES_ID = "SELECT company.name FROM company WHERE id = %d";
    private static final String QUERRY_LIST_COMPUTER = "SELECT computer.id FROM computer RIGHT JOIN company ON computer.company_id = %d ";
    private static final String OFFSET_LIMIT = " LIMIT %d OFFSET %d";
    private static final String DELETE_COMPANY = "DELETE FROM company WHERE id = %d";
    private static final String QUERRY_COUNT = "SELECT COUNT(*) FROM company";



    /**
     * 
     * @param id id de la compagnie
     * @return
     */
    public List<Integer> computerFromCompany(int id){
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        return vJdbcTemplate.query(String.format(QUERRY_LIST_COMPUTER,id),mapperInteger);
    }


    /**
     *
     * @param id d'une compagnie
     * @return true si la compagnie exist, false sinon
     */
    public boolean existCompanies(int id) {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        return !vJdbcTemplate.query(String.format(QUERRY_LIST_COMPANIES_ID,id),mapperCompany).isEmpty();
    }

    /**
     *
     * @return la liste des compagnies
     */
    public List<Company> listCompanies() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        return vJdbcTemplate.query(String.format(QUERRY_LIST_COMPANIES),mapperCompany);
    }

    /**
     *
     * @param id l'id
     */
    public void deleteCompany(int id) {
        List<Integer> computerIds = computerFromCompany(id);
        computerDAO.deleteListComputer(computerIds);
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        vJdbcTemplate.update(String.format(DELETE_COMPANY,id));
    }

    /**
     *
     * @param offset l'offset
     * @param limit le nombre a afficher
     * @return la liste des compagnies
     * @throws CompaniesInexistantException erreur sur la compagnie de l'ordinateur
     */
    public List<Company> listCompany(int offset, int limit) {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        return vJdbcTemplate.query(QUERRY_LIST_COMPANIES + String.format(OFFSET_LIMIT,limit,offset), mapperCompany);
    }

    /**
     *
     * @param id de la compagnie
     * @return la compagnie
     * @throws CompaniesInexistantException la compagnie recherché n'existe pas
     */
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

    /**
     *
     */
    public int getCount() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        return vJdbcTemplate.queryForObject(QUERRY_COUNT,Integer.class);
    }

    /**
     *
     * @param name le nom de l'ordinateur
     * @return la liste des Id
     */
    public List<Integer> getIdFromName(String name) {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(dataSource);
        return vJdbcTemplate.query(String.format(QUERRY_LIST_COMPANIES_BY_NAME, name), mapperInteger);
    }

}