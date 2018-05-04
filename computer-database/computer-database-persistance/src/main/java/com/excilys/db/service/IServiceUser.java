package com.excilys.db.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.excilys.db.model.Users;

public interface IServiceUser {

    void addUser(Users user);

    void updateUser(Users user);

    Optional<Users> getUser(String pseudo);

    UserDetails loadUserByUsername(String pseudo) throws UsernameNotFoundException;

}