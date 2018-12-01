package com.example.linksShortener.controller.api;

import com.example.linksShortener.bean.Link;
import com.example.linksShortener.service.ILinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LinksController {

    @Autowired
    private ILinkService linkService;

    @RequestMapping("${spring.data.rest.base-path}/links")
    public List<Link> findLinks() {

        return linkService.findAll();
    }

    @RequestMapping("${spring.data.rest.base-path}/links/{shortUrl}")
    public Link findCity(@PathVariable String shortUrl) {

        return linkService.findByShortUrl(shortUrl);
    }
}