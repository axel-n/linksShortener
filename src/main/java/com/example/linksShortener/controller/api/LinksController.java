package com.example.linksShortener.controller.api;

import com.example.linksShortener.model.Link;
import com.example.linksShortener.service.ILinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    // @RequestMapping("/{shortUrl}")
    @GetMapping("/{shortUrl}")
    public String findLink(@PathVariable String shortUrl) {

        Link link = linkService.findByShortUrl(shortUrl);

        if (link != null) {
            return linkService.findByShortUrl(shortUrl).getLongUrl();
        } else {
            // TODO
            // переделать в нормальные исключения
            return shortUrl + " URL not Found";
        }

    }
}