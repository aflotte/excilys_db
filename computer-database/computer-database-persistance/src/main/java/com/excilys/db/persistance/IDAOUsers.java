package com.excilys.db.persistance;

import org.hibernate.Session;

import com.excilys.db.model.User;


public interface IDAOUsers {

    void addUser(User user);

    void updateUser(User user);

    User getUser(String username);

    void adduserRole(User user, Session session);

}