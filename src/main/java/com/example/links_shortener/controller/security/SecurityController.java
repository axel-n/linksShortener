package com.example.links_shortener.controller.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SecurityController {

    @RequestMapping("/user/login")
    public String viewLoginForm() {

        return "user/login";
    }

    @PostMapping(value = "/user/login")
    public String validateUser() {

        System.out.println("user submit form");

        return "user/dashboard";
    }

    @RequestMapping("/user/dashboard")
    public String dashboard() {

        return "user/dashboard";
    }
}
