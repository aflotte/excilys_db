package com.excilys.db.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.db.persistance.DAOUsers;
import com.excilys.db.persistance.IDAOUsers;
import com.excilys.db.service.IServiceUser;
import com.excilys.db.model.Users;

@Service
@Transactional
public class UserService implements UserDetailsService, IServiceUser{
    
    @Autowired
    private IDAOUsers userDAO;

    
    /* (non-Javadoc)
     * @see com.excilys.db.config.IServiceUser#addUser(com.excilys.db.model.Users)
     */
    @Override
    public void addUser(Users user) {
        userDAO.addUser(user);
        userDAO.addUserRole(user);
    }
    
    /* (non-Javadoc)
     * @see com.excilys.db.config.IServiceUser#updateUser(com.excilys.db.model.Users)
     */
    @Override
    public void updateUser(Users user) {
        userDAO.updateUser(user);
    }
    
    /* (non-Javadoc)
     * @see com.excilys.db.config.IServiceUser#getUser(java.lang.String)
     */
    @Override
    public Optional<Users> getUser(String pseudo) {
        return userDAO.getUser(pseudo);
    }

    /* (non-Javadoc)
     * @see com.excilys.db.config.IServiceUser#loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String pseudo) throws UsernameNotFoundException {
        Optional<Users> user = userDAO.getUser(pseudo);
        if(user.isPresent()) {
            Users users = user.get();
            return User.withUsername(pseudo).password(users.getPassword()).roles(userDAO.getUserRole(pseudo)).build(); 
        }
        return null;
    }
}