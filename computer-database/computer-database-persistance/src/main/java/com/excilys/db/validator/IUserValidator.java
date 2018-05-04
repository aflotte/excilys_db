package com.excilys.db.validator;

import org.springframework.security.core.Authentication;

import com.excilys.db.exception.DroitInsuffisantException;
import com.excilys.db.exception.UserAlreadyExistException;

public interface IUserValidator {

    Boolean controleAuth(Authentication auth) throws DroitInsuffisantException;

    Boolean isUserExist(String username) throws UserAlreadyExistException;

    Boolean controleText(String text) throws IllegalCharacterException;

}