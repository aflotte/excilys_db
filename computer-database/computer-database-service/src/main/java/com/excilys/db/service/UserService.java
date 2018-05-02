package com.excilys.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.db.model.User;
import com.excilys.db.persistance.IDAOUsers;

@Service
public class UserService implements IUserService {
    @Autowired
    private IDAOUsers daouser;
    
    public UserService(IDAOUsers daouser) {
        this.daouser = daouser;
    }

    /* (non-Javadoc)
     * @see com.excilys.db.service.IUserService#addUser(com.excilys.db.model.User)
     */
    @Override
    public void addUser(User user) {
        daouser.addUser(user);
    }
    
    /* (non-Javadoc)
     * @see com.excilys.db.service.IUserService#updateUser(com.excilys.db.model.User)
     */
    @Override
    public void updateUser(User user) {
        daouser.updateUser(user);
    }
    
    /* (non-Javadoc)
     * @see com.excilys.db.service.IUserService#getUser(java.lang.String)
     */
    @Override
    public User getUser(String username) {
        return daouser.getUser(username);
}
}