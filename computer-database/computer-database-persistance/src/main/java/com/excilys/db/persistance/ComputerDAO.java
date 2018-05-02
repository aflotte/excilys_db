package com.excilys.db.persistance;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import com.excilys.db.model.Computer;

import javax.persistence.TypedQuery;
/**
 *
 * @author flotte
 */
@Repository("computerDAO")
public class ComputerDAO implements IComputerDAO {
    @Autowired
    SessionFactory sessionFactory;
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerDAO.class);

    private static final String QUERRY_LIST_COMPUTERS = "FROM " +  Computer.class.getName();
    private static final String QUERRY_LIST_COMPUTERS_ID = "FROM " + Computer.class.getName() + " computer WHERE computer.id = %d ";
    private static final String QUERRY_LIST_COMPUTER_BY_NAME = "SELECT computer.id FROM " + Computer.class.getName() + " computer WHERE computer.name = '%s' ";
    private static final String ORDER_BY = " ORDER BY %s %s ";
    private static final String ORDER_BY_COMPUTER = " computer ORDER BY %s %s ";
    private static final String QUERRY_COUNT = "SELECT COUNT(*) FROM " + Computer.class.getName();
    private static final String LIKE = " computer WHERE computer IN ((FROM " + Computer.class.getName() + " computer1 WHERE computer1.name LIKE \'%%%s%%\'),(FROM " + Computer.class.getName() + " computer2 WHERE computer2.company.name LIKE \'%%%s%%\'))";

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#listComputer()
     */
    @Override
    public List<Computer> listComputer() {
        try (Session session = sessionFactory.openSession();){
            TypedQuery<Computer> querry = session.createQuery(QUERRY_LIST_COMPUTERS,Computer.class);
            return querry.getResultList();
        }
    }


    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#listComputer(int, int, java.lang.String, java.lang.String)
     */
    @Override
    public List<Computer> listComputer(int offset, int limit,String sortBy, String orderBy ) {
        try (Session session = sessionFactory.openSession();){
            TypedQuery<Computer> querry = session.createQuery(QUERRY_LIST_COMPUTERS + String.format(ORDER_BY_COMPUTER, sortBy, orderBy), Computer.class);
            querry.setMaxResults(limit);
            querry.setFirstResult(offset);
            return querry.getResultList();
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#showDetails(int)
     */
    @Override
    public Optional<Computer> showDetails(int id) {
        try (Session session = sessionFactory.openSession();){
            TypedQuery<Computer> querry = session.createQuery(String.format(QUERRY_LIST_COMPUTERS_ID,id), Computer.class);
            List<Computer> results = querry.getResultList();
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
     * @see com.excilys.db.persistance.IComputerDAO#updateAComputer(com.excilys.db.model.Computer, int)
     */
    @Override
    public void updateAComputer(Computer computer, int id) {
        try (Session session = sessionFactory.openSession();){
            computer.setId(id);
            session.getTransaction().begin();
            session.update(computer);
            session.getTransaction().commit();
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#createAComputer(com.excilys.db.model.Computer)
     */
    @Override
    public int createAComputer(Computer computer) {
        try (Session session = sessionFactory.openSession();){
            return (int)session.save(computer);
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#getIdFromName(java.lang.String)
     */
    @Override
    public List<Integer> getIdFromName(String name) {
        try (Session session = sessionFactory.openSession();){
            TypedQuery<Integer> querry = session.createQuery(QUERRY_LIST_COMPUTER_BY_NAME,Integer.class);
            return querry.getResultList();
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#deleteAComputer(int)
     */
    @Override
    public void deleteAComputer(int id) {
        try (Session session = sessionFactory.openSession();){
            Optional<Computer> computer = showDetails(id);

            if (computer.isPresent()) {
                session.getTransaction().begin();
                session.delete(computer.get());
                session.getTransaction().commit();
            }

        }
    }


    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#deleteListComputer(int[])
     */
    @Override
    public void deleteListComputer(List<Integer> ids) {
        try (Session session = sessionFactory.openSession();){
            session.getTransaction().begin();
            for (int i=0;i < ids.size();i++) {
                Optional<Computer> computer = showDetails(ids.get(i));
                if (computer.isPresent()) {
                    session.delete(computer.get());
                    if (i%19 == 0) {
                        session.flush();
                        session.clear();
                    }
                }
            }
            session.getTransaction().commit();
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#deleteListComputer(int[])
     */
    @Override
    public void deleteListComputer(List<Integer> ids, Session session) {
        if (session != null) {
            for (int i=0;i < ids.size();i++) {
                deleteAComputer(ids.get(i));
                if (i%19 == 0) {
                    session.flush();
                    session.clear();
                }
            }
        } else {
            deleteListComputer(ids);
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#getCount()
     */
    @Override
    public int getCount() {
        try (Session session = sessionFactory.openSession();){
            Query<Long> querry = session.createQuery(QUERRY_COUNT,Long.class);
            return querry.uniqueResult().intValue();
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#getCount(java.lang.String)
     */
    @Override
    public int getCount(String search) {
        try (Session session = sessionFactory.openSession();){
            Query<Long> querry = session.createQuery(QUERRY_COUNT + String.format(LIKE, search,search),Long.class);
            return querry.uniqueResult().intValue();
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IComputerDAO#listComputerLike(int, int, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<Computer> listComputerLike(int offset, int limit, String name, String sortBy, String orderBy) {
        try (Session session = sessionFactory.openSession();){
            TypedQuery<Computer> querry = session.createQuery(QUERRY_LIST_COMPUTERS + String.format(LIKE, name, name) + String.format(ORDER_BY, sortBy, orderBy), Computer.class);
            querry.setMaxResults(limit);
            querry.setFirstResult(offset);
            return querry.getResultList();
        }
    }

}