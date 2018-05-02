package com.excilys.db.controller;

import java.util.Locale;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.db.dto.UserDTO;
import com.excilys.db.exception.DroitInsuffisantException;
import com.excilys.db.exception.UserAlreadyExistException;
import com.excilys.db.mapper.UserMapper;
import com.excilys.db.model.User;
import com.excilys.db.service.IUserService;
import com.excilys.db.validator.UserValidator;

@Controller
public class UserController {
    private final UserValidator validator;
    private final IUserService serviceUser;
    private final UserMapper mapperUser;

    public UserController(UserValidator validator, IUserService serviceUser, UserMapper mapperUser) {
        this.validator = validator;
        this.serviceUser = serviceUser;
        this.mapperUser = mapperUser;
    }

    @GetMapping("login")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;
    }

    @PostMapping("login")
    public ModelAndView loginPost(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;
    }
    
    
    public User enteredUser(UserDTO dtouser) throws UserAlreadyExistException {
        validator.isUserExist(dtouser.getUsername());
        return mapperUser.toUser(dtouser);
    }
    public User enteredUpdateUser(UserDTO dtouser) {
        return mapperUser.toUser(dtouser);
    }

    @GetMapping("addUser")
    public String addUser(ModelMap model, @RequestParam Map<String, String> params, RedirectAttributes redir, Locale locale) {
        model.addAttribute("DTOUser", new UserDTO());
        return "addUser";
    }

    @PostMapping("addUser")
    public String postUser(@ModelAttribute("DTOUser") UserDTO dtouser, ModelMap model, RedirectAttributes redir, Locale locale) {
        User user = null;
        try {
            user = enteredUser(dtouser);
        }catch (UserAlreadyExistException e) {
            redir.addFlashAttribute("error", e.getClass().getSimpleName());
            return "redirect:addUser";
        }

        serviceUser.addUser(user);
        return "redirect:dashboard";
    }

    @GetMapping("updateUser")
    public String updateUser(ModelMap model, @RequestParam Map<String, String> params, RedirectAttributes redir, Locale locale) throws DroitInsuffisantException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        validator.controleAuth(auth);

        String username = params.get("username");
        User user = serviceUser.getUser(username);
        UserDTO dtoUser = mapperUser.toDTo(user);
        model.addAttribute("dtoUser", dtoUser);
        model.addAttribute("DTOUser", new UserDTO());
        return "updateUser";
    }

    @PostMapping("updateUser")
    public String postUpdateUser(@ModelAttribute("DTOUser") UserDTO dtouser, ModelMap model, RedirectAttributes redir, Locale locale) {
        User user = null;
        user = enteredUpdateUser(dtouser);

        serviceUser.updateUser(user);
        return "redirect:dashboard";
    }


}
