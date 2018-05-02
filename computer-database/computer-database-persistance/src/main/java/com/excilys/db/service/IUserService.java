package com.excilys.db.service;

import com.excilys.db.model.User;

public interface IUserService {

    void addUser(User user);

    void updateUser(User user);

    User getUser(String username);

}