package com.example.links_shortener.controller.security;

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
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;


@Controller
public class SecurityController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private ArrayList<HashMap<String, String>> messages = new ArrayList<>();
    private Message message;

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/user/registration")
    public String showRegistrationForm(Model model) {

        log.info("Rendering registration page.");

        model.addAttribute("user", new UserDto());

        return "user/registration";
    }

    @PostMapping(value = "/user/registration")
    public String registerUserAccount(@Valid final UserDto accountDto, BindingResult bindingResult, Model model) {

        final User registered = userService.registerNewUserAccount(accountDto);

        if (registered != null) {
            log.info("registered user {}", registered);
            return "redirect:/user/login?registration";
        } else {
            message = new Message("warning", "Email not unique!");
            messages.add(message.getMessage());
            model.addAttribute("messages", messages);

            return "user/login";
        }
    }

    @RequestMapping("/user/dashboard")
    public String dashboard() {
        return "user/dashboard";
    }

    @RequestMapping("/user/login")
    public String viewLoginForm() {

        return "user/login";
    }
}
