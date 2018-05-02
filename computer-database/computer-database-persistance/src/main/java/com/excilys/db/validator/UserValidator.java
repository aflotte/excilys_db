package com.excilys.db.validator;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.excilys.db.exception.DroitInsuffisantException;
import com.excilys.db.exception.UserAlreadyExistException;
import com.excilys.db.service.IUserService;

@Component
public class UserValidator {

    private final IUserService serviceUser;

    public UserValidator(IUserService serviceUser) {
        this.serviceUser = serviceUser;
    }


    public Boolean controleAuth(Authentication auth) throws DroitInsuffisantException {
        if ((auth instanceof AnonymousAuthenticationToken)) {
            throw new DroitInsuffisantException();
        }
        return true;
    }

    public Boolean isUserExist(String username) throws UserAlreadyExistException {
        if (serviceUser.getUser(username) != null) {
            throw new UserAlreadyExistException();
        }
        return true;
    }
}
