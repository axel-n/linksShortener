package com.example.links_shortener.controller.security;

import com.example.links_shortener.dao.UserRepository;
import com.example.links_shortener.dto.UserDto;
import com.example.links_shortener.model.User;
import com.example.links_shortener.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.validation.Valid;
import java.util.*;

@Controller
public class SecurityController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private ArrayList<HashMap<String, String>> messages = new ArrayList<>();
    private Message message;

    @Autowired
    private IUserService userService;

    @Autowired
    private UserRepository repository;

    @RequestMapping(value = "/user/registration")
    public String showRegistrationForm(Model model) {

        log.info("Rendering registration page.");

        model.addAttribute("user", new UserDto());

        return "user/registration";
    }

    @PostMapping(value = "/user/registration")
    public String registerUserAccount(@Valid final UserDto accountDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "user/registration";
        }

        final User registered = userService.registerNewUserAccount(accountDto);

        if (registered == null) {

            message = new Message("warning", "Email not unique!");
            messages.add(message.getMessage());
            model.addAttribute("messages", messages);

            return "user/registration";
        }

        log.info("registered account with information: {}", registered);

        message = new Message("success", "Registration complete!");
        messages.add(message.getMessage());
        model.addAttribute("messages", messages);

        return "user/login";
    }


    @RequestMapping("/user/login")
    public String viewLoginForm() {

        return "user/login";
    }

    @PostMapping(value = "/user/login")
    public String validateUser(final User user, Model model) {

        User foundUser = repository.findByEmail(user.getEmail());

        if (checkUser(user.getPassword(), foundUser)) {

            model.addAttribute("user", foundUser);
            return "user/dashboard";
        } else

            message = new Message("warning", "Email or password not correct!");
            messages.add(message.getMessage());
            model.addAttribute("messages", messages);

            return "user/login";
    }

    private boolean checkUser(String password, User foundUser) {
        return (foundUser != null) && password.equals(foundUser.getPassword());
    }
}
