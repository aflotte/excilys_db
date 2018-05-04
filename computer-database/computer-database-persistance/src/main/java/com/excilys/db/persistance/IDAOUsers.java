package com.excilys.db.persistance;

import java.util.Optional;

import org.hibernate.Session;

import com.excilys.db.model.Users;


public interface IDAOUsers {

    void addUser(Users user);

    void updateUser(Users user);

    Optional<Users> getUser(String username);

    void adduserRole(Users user, Session session);

    void addUserRole(Users user);

    public String getUserRole(String pseudo);
    
}