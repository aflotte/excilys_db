package com.excilys.db.controller;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.db.dto.UserDTO;
import com.excilys.db.exception.UserAlreadyExistException;
import com.excilys.db.mapper.UserMapper;
import com.excilys.db.model.Users;
import com.excilys.db.service.IServiceUser;
import com.excilys.db.validator.IUserValidator;

@Controller
public class UserController {

    @Autowired
    IServiceUser serviceUser;
    
    @Autowired
    IUserValidator validator;
    
    
    @Autowired
    UserMapper mapperUser;


    @GetMapping("addUser")
    public String addUser(ModelMap model, @RequestParam Map<String, String> params, RedirectAttributes redir, Locale locale) {
        model.addAttribute("DTOUser", new UserDTO());
        return "addUser";
    }

    @PostMapping("addUser")
    public String postUser(@ModelAttribute("DTOUser") UserDTO dtouser, ModelMap model, RedirectAttributes redir, Locale locale) throws com.excilys.db.validator.IllegalCharacterException {
        Users user = null;
        try {
            user = enteredUser(dtouser);
        }catch (IllegalCharacterException | UserAlreadyExistException e) {
            redir.addFlashAttribute("error", e.getClass().getSimpleName());
            return "redirect:addUser";
        }

        serviceUser.addUser(user);
        return "redirect:dashboard";
    }


    public Users enteredUser(UserDTO dtouser) throws IllegalCharacterException, UserAlreadyExistException, com.excilys.db.validator.IllegalCharacterException {
        validator.controleText(dtouser.getUsername());
        validator.controleText(dtouser.getPassword());
        validator.isUserExist(dtouser.getUsername());
        Users user = mapperUser.toUser(dtouser);
        return user;
    }
}
