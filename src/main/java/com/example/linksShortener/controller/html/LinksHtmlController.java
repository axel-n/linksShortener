package com.example.linksShortener.controller.html;

import com.example.linksShortener.bean.Link;
import com.example.linksShortener.service.ILinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class LinksHtmlController {

    @Autowired
    private ILinkService linkService;

    @RequestMapping("/")
    public String index() {

        return "home";
    }

    @RequestMapping("/links")
    public ModelAndView showLinks() {

        List<Link> links = linkService.findAll();

        Map<String, Object> params = new HashMap<>();
        params.put("links", links);

        return new ModelAndView("links", params);
    }

    @RequestMapping("/api")
    public String apiDoc() {

        return "apiDoc";
    }
}