package com.example.links_shortener.controller.links;

import com.example.links_shortener.core.dto.LinkDto;
import com.example.links_shortener.core.model.Link;
import com.example.links_shortener.core.service.ILinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import javax.validation.Valid;

@Controller
public class WebController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ILinkService linkService;

    @RequestMapping(value="/")
    public String index(Model model) {

        model.addAttribute("link", new Link());

        return "home";
    }
    @PostMapping(value = "/link")
    public String addLinkFromHomepage(@Valid LinkDto linkDto, Model model, Authentication authentication) {

        Link link = linkService.addLink(linkDto.getLongUrl(), authentication);

        model.addAttribute("link", link);

        return "home";
    }

    @PostMapping(value = "/user/link")
    public String addLinkFromDashboard(@Valid LinkDto linkDto, Model model, Authentication authentication) {

        Link link = linkService.addLink(linkDto.getLongUrl(), authentication);

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