package com.example.links_shortener.controller.links;


import com.example.links_shortener.dao.LinkRepository;
import com.example.links_shortener.model.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class APIController {

    @Autowired
    private LinkRepository linkRepository;

    @RequestMapping("${spring.data.rest.base-path}/links")
    public Iterable<Link> findAllLinks() {

        return linkRepository.findAll();
    }
    @GetMapping("/{shortUrl}")
    @ResponseBody
    public String findOneLink(@PathVariable String shortUrl, HttpServletResponse response) {

        if (shortUrl.length() < 5) {
            response.setStatus( HttpServletResponse.SC_NOT_FOUND);
            return "link not found";
        }

        Link link = linkRepository.findByShortUrl(shortUrl);

        if (link != null) {
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            link.setClicks();
            linkRepository.save(link);
            response.setHeader("Location", link.getLongUrl());
            return "go to the long url";
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "link not found";
        }
    }
}


