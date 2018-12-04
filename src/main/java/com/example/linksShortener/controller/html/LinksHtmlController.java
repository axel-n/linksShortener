package com.example.linksShortener.controller.html;

import com.example.linksShortener.model.Link;
import com.example.linksShortener.repository.ILinkRepository;
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
    private ILinkRepository linkService;

    @RequestMapping("/")
    public String index() {

        // init for test
        // TODO
        // 1. переместить тесты в нужное место
        // 2. добавить генерацию уникальной короткой ссылки
        Link link1 = new Link("https://ya.ru");
        int result = linkService.saveLink(link1);
        // end init for test

        return "home";
    }

    @RequestMapping("/user/links")
    public ModelAndView showLinks() {
        List<Link> links = linkService.findAll();

        Map<String, Object> params = new HashMap<>();
        params.put("links", links);

        System.out.println(links);

        return new ModelAndView("links", params);
    }

    @RequestMapping("/api")
    public String apiDoc() {

        return "apiDoc";
    }
}