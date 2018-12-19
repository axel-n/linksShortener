package com.example.links_shortener.controller.links;

import com.example.links_shortener.dao.UserRepository;
import com.example.links_shortener.model.Link;
import com.example.links_shortener.dao.LinkRepository;

import com.example.links_shortener.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class WebController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value="/")
    public String index(Model model) {

        model.addAttribute("link", new Link());

        return "home";
    }

    private Link addLink(String longUrl, Authentication authentication) {

        Link link = new Link(longUrl);

        if (authentication != null) {
            User loggedUser = userRepository.findByEmail(authentication.getName());
            link.setUserId(loggedUser.getId());
        }

        link = linkRepository.save(link);
        log.info("save {}", link);

        return link;
    }

    @PostMapping(value = "/link")

    public String addLinkFromHomepage(@ModelAttribute Link linkDto, Model model, Authentication authentication) {

        Link link = addLink(linkDto.getLongUrl(), authentication);

        model.addAttribute("link", link);

        return "home";
    }

    @PostMapping(value = "/user/link")
    // from a index page (logged and not logged users)
    public String addLinkFromDashboard(@ModelAttribute Link linkDto, Model model, Authentication authentication) {

        Link link = addLink(linkDto.getLongUrl(), authentication);

        model.addAttribute("link", link);

        return "redirect:/user/dashboard";
    }

    @RequestMapping("/api")
    public String apiDocumentation() {

        return "api/documentation";
    }

    @RequestMapping("/about")
    public String about() {

        return "pages/about";
    }
}