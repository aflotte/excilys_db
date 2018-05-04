package com.excilys.db.persistance;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.db.model.Users;
import com.excilys.db.model.UserRoles;

@Repository("daoUsers")
public class DAOUsers implements IDAOUsers {

    @Autowired
    SessionFactory sessionFactory;
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DAOUsers.class);
    String GET_USER = "From " + Users.class.getName() + " user WHERE user.username = '%s' ";


    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IDAOUsers#addUser(com.excilys.db.model.User)
     */
    @Override
    public void addUser(Users user) {
        try (Session session = sessionFactory.openSession();){
            session.save(user);
            adduserRole(user, session);
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IDAOUsers#adduserRole(com.excilys.db.model.User)
     */
    @Override
    public void adduserRole(Users user,Session session) {
        UserRoles userRole = new UserRoles();
        userRole.setUser(user);
        session.save(userRole);
    }


    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IDAOUsers#adduserRole(com.excilys.db.model.User)
     */
    @Override
    public void addUserRole(Users user) {
        try (Session session = sessionFactory.openSession();){
            UserRoles userRole = new UserRoles();
            userRole.setUser(user);
            session.save(userRole);
        }
    }


    /* (non-Javadoc)
     * @see com.excilys.db.persistance.IDAOUsers#updateUser(com.excilys.db.model.User)
     */
    @Override
    public void updateUser(Users user) {
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

    public Optional<Users> getUser(String pseudo) {
        try (Session session = sessionFactory.openSession();) {
            TypedQuery<Users> query = session.createQuery("FROM " + Users.class.getName() + " users WHERE users.username=?", Users.class)
                    .setParameter(0, pseudo);
            Users user = ((Query<Users>) query).uniqueResult();
            return Optional.ofNullable(user);
        }
    }
    
    public String getUserRole(String pseudo) {
        try (Session session = sessionFactory.openSession();) {
            TypedQuery<UserRoles> query = session.createQuery("FROM " + UserRoles.class.getName() + " roles WHERE roles.user.username='" + pseudo + "'", UserRoles.class);
            UserRoles role = ((Query<UserRoles>) query).uniqueResult();
            return role.getRole();
        }
}
}
