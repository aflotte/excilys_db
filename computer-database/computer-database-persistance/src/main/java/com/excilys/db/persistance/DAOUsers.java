package com.excilys.db.persistance;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.db.model.User;
import com.excilys.db.model.UserRoles;

@Repository("daoUsers")
public class DAOUsers implements IDAOUsers {

    @Autowired
    SessionFactory sessionFactory;
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DAOUsers.class);
    String GET_USER = "From " + User.class.getName() + " user WHERE user.username = '%s' ";


    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IDAOUsers#addUser(com.excilys.db.model.User)
     */
    @Override
    public void addUser(User user) {
        try (Session session = sessionFactory.openSession();){
            session.save(user);
            adduserRole(user, session);
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IDAOUsers#adduserRole(com.excilys.db.model.User)
     */
    @Override
    public void adduserRole(User user,Session session) {
        UserRoles userRole = new UserRoles();
        userRole.setUser(user);
        session.save(userRole);
    
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IDAOUsers#updateUser(com.excilys.db.model.User)
     */
    @Override
    public void updateUser(User user) {
        try (Session session = sessionFactory.openSession();){
            session.getTransaction().begin();
            session.update(user);
            session.getTransaction().commit();
        }
    
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IDAOUsers#getUser(java.lang.String)
     */
    @Override
    public User getUser(String username) {
        try (Session session = sessionFactory.openSession();){
            TypedQuery<User> querry = session.createQuery(String.format(GET_USER, username), User.class);
            List<User> result = querry.getResultList();
            if (result.isEmpty()) {
                return null;
            }
            return result.get(0);
        }
    }
}
