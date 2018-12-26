package com.example.links_shortener.controller.security;

import com.example.links_shortener.config.TypeMessage;
import com.example.links_shortener.core.dto.UserDto;
import com.example.links_shortener.core.model.Link;
import com.example.links_shortener.core.model.User;
import com.example.links_shortener.core.service.LinkService;
import com.example.links_shortener.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    private UserService userService;

    @Autowired
    private LinkService linkService;

    @RequestMapping(value = "/user/registration")
    public String showRegistrationForm(Model model) {

        log.info("Rendering registration page.");

        model.addAttribute("user", new UserDto());

        return "user/registration";
    }

    @PostMapping(value = "/user/registration")
    public ModelAndView registerUserAccount(@Valid final UserDto accountDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return new ModelAndView("user/registration", "user", accountDto);
        } else {
            final User registered = userService.registerNewUserAccount(accountDto);

            if (registered == null) {

                message = new Message(TypeMessage.WARNING, "email already registered!");
                messages.add(message.getMessage());
                model.addAttribute("messages", messages);

                return new ModelAndView("user/login");
            } else {
                return new ModelAndView("redirect:/user/login?registration");
            }
        }
    }

    @RequestMapping("/user/dashboard")
    public String dashboard(Authentication authentication, Model model) {

        int userId = userService.findByEmail(authentication.getName()).getId();

        model.addAttribute("savedLinks", linkService.findByUserId(userId));

        model.addAttribute("link", new Link());

        return "user/dashboard";
    }

    @RequestMapping("/user/login")
    public String viewLoginForm() {

        return "user/login";
    }
}
