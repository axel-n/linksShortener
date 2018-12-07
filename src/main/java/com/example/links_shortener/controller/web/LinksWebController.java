package com.example.links_shortener.controller.web;

import com.example.links_shortener.model.Link;
import com.example.links_shortener.repository.LinkRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class LinksWebController {

    @Autowired
    private LinkRepository linkRepository;

    @RequestMapping("/")
    public String index() {

        return "home";
    }

    @RequestMapping("/user/links")
    public ModelAndView showLinks() {
        List<Link> links = linkRepository.findAll();

        Map<String, Object> params = new HashMap<>();
        params.put("links", links);

        return new ModelAndView("links", params);
    }

    @RequestMapping("/api")
    public String apiDoc() {

        return "apiDoc";
    }
}