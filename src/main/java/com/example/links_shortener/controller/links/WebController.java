package com.example.links_shortener.controller.links;

import com.example.links_shortener.model.Link;
import com.example.links_shortener.dao.LinkRepository;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private LinkRepository linkRepository;

    @RequestMapping(value="/")
    public String index(Model model) {

        model.addAttribute("link", new Link());

        return "home";
    }

    @RequestMapping("/user/links")
    public ModelAndView showLinks() {
        List<Link> links = linkRepository.findAll();

        Map<String, Object> params = new HashMap<>();
        params.put("links", links);

        return new ModelAndView("link/linksList", params);
    }

    @PostMapping(value = "/")
    public String addLink(@ModelAttribute Link link, Model model) {

        model.addAttribute("link", link);

        linkRepository.save(link);

        return "link/linkView";
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