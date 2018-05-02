package com.excilys.db.persistance;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.db.model.Company;
import com.excilys.db.model.Computer;

/**
 * La classe DAO de Companies.
 * @author flotte
 *
 */
@Repository("companiesDAO")
public class CompaniesDAO implements ICompaniesDAO {
    @Autowired
    SessionFactory sessionFactory;
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompaniesDAO.class);
    @Autowired
    private IComputerDAO computerDAO;


    private static final String QUERRY_LIST_COMPANIES_BY_NAME = "SELECT company.id FROM " + Company.class.getName() + " company WHERE company.name LIKE \'%s\'";
    private static final String QUERRY_LIST_COMPANIES = "FROM " + Company.class.getName();
    private static final String QUERRY_LIST_COMPANIES_ID = "FROM " + Company.class.getName() + " company WHERE company.id = %d";
    private static final String QUERRY_LIST_COMPUTER = "SELECT computer.id FROM " + Computer.class.getName() + " computer WHERE computer.company.id = %d ";
    private static final String QUERRY_COUNT = "SELECT COUNT(*) FROM " + Company.class.getName();



    /* (non-Javadoc)
     * @see com.excilys.db.persistance.ICompaniesDAO#computerFromCompany(int)
     */
    @Override
    public List<Integer> computerFromCompany(int id){
        try (Session session = sessionFactory.openSession();){
            TypedQuery<Integer> querry = session.createQuery(String.format(QUERRY_LIST_COMPUTER,id),Integer.class);
            return querry.getResultList();
        }
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
        try (Session session = sessionFactory.openSession();){
            Optional<Company> company = getCompany(id);
            if (company.isPresent()) {
                session.getTransaction().begin();
                List<Integer> computerIds = computerFromCompany(id);
                computerDAO.deleteListComputer(computerIds,session);
                session.delete(company.get());
                session.getTransaction().commit();
            }
        }
    }




    /* (non-Javadoc)
     * @see com.excilys.db.persistance.ICompaniesDAO#listCompany(int, int)
     */
    @Override
    public List<Company> listCompany(int offset, int limit) {
        try(Session session = sessionFactory.openSession();){
            TypedQuery<Company> querry = session.createQuery(QUERRY_LIST_COMPANIES,Company.class);
            querry.setFirstResult(offset);
            querry.setMaxResults(limit);
            return querry.getResultList();
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.ICompaniesDAO#getCompany(java.lang.Integer)
     */
    @Override
    public Optional<Company> getCompany(Integer id) {
        try (Session session = sessionFactory.openSession();){
            TypedQuery<Company> querry = session.createQuery(String.format(QUERRY_LIST_COMPANIES_ID,id), Company.class);
            List<Company> results = querry.getResultList();
            if (results == null) {
                return Optional.ofNullable(null);
            } else if (results.isEmpty()){
                return Optional.ofNullable(null);
            }else {
                return Optional.ofNullable(results.get(0));
            }
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.ICompaniesDAO#getCount()
     */
    @Override
    public int getCount() {
        try (Session session = sessionFactory.openSession();){
            Query<Long> querry = session.createQuery(QUERRY_COUNT,Long.class);
            return querry.uniqueResult().intValue();
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.ICompaniesDAO#getIdFromName(java.lang.String)
     */
    @Override
    public List<Integer> getIdFromName(String name) {
        try (Session session = sessionFactory.openSession();){
            TypedQuery<Integer> querry = session.createQuery(String.format(QUERRY_LIST_COMPANIES_BY_NAME,name),Integer.class);
            return querry.getResultList();
        }
    }

}