package com.example.links_shortener.controller.security;

import com.example.links_shortener.dao.UserRepository;
import com.example.links_shortener.dto.UserDto;
import com.example.links_shortener.error.EmailExistsException;
import com.example.links_shortener.model.User;
import com.example.links_shortener.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class SecurityController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserService userService;

    @Autowired
    private UserRepository repository;

    @RequestMapping(value = "/user/registration")
    public String showRegistrationForm(Model model) {

        log.info("Rendering registration page.");

        model.addAttribute("user",  new UserDto());
        return "user/registration";
    }

    @PostMapping(value = "/user/registration")
    public String registerUserAccount(@Valid final UserDto accountDto, Model model, HttpServletRequest request) throws EmailExistsException {

        log.info("Registering user account with information: {}", accountDto);
        try {
            final User registered = userService.registerNewUserAccount(accountDto);

            log.info("registered! account information: {}", registered);

            return "user/success-registration";

        } catch (EmailExistsException e) {
            log.info("account {} already exist!", accountDto.getEmail());
        }

        model.addAttribute("error", "some text error");
        return "user/registration";
    }


    @RequestMapping("/user/login")
    public String viewLoginForm() {

        return "user/login";
    }

    @PostMapping(value = "/user/login")
    public String validateUser(final User user, Model model) {

        log.info("user submit form email: {}, password: {}", user.getEmail(), user.getPassword());

        User foundUser = repository.findByEmail(user.getEmail());

        if ((foundUser != null) && (user.getPassword().equals(foundUser.getPassword()))) {

            log.info("user founded with data {}", foundUser);

            model.addAttribute("user", foundUser);
            return "user/dashboard";
        } else
            // TODO
            // add tips for user
            return "user/login";
    }
}
