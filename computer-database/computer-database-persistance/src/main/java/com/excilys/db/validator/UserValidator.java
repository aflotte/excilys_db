package com.excilys.db.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.excilys.db.exception.DroitInsuffisantException;
import com.excilys.db.exception.UserAlreadyExistException;
import com.excilys.db.service.IServiceUser;

@Component
public class UserValidator implements IUserValidator {

    @Autowired
    IServiceUser serviceUser;

    /* (non-Javadoc)
     * @see com.excilys.db.validator.IUserValidator#controleAuth(org.springframework.security.core.Authentication)
     */
    @Override
    public Boolean controleAuth(Authentication auth) throws DroitInsuffisantException {
        if ((auth instanceof AnonymousAuthenticationToken)) {
            throw new DroitInsuffisantException();
        }
        return true;
    }

    /* (non-Javadoc)
     * @see com.excilys.db.validator.IUserValidator#isUserExist(java.lang.String)
     */
    @Override
    public Boolean isUserExist(String username) throws UserAlreadyExistException {
        if (serviceUser.getUser(username) != null) {
            throw new UserAlreadyExistException();
        }
        return true;
    }
    

    /* (non-Javadoc)
     * @see com.excilys.db.validator.IUserValidator#controleText(java.lang.String)
     */
    @Override
    public Boolean controleText(String text) throws IllegalCharacterException{
        char[] interdit = {'[', '!', '@', '#', '%', '^', '&', '*', '(', ')', '<', '>', ']'};
        for (char lettre : interdit) {
            if (text.indexOf(lettre) >= 0) {
                throw new IllegalCharacterException();
            }
        }
        return true;
}
}
