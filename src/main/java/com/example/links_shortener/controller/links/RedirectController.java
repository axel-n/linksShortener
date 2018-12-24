package com.example.links_shortener.controller.links;

import com.example.links_shortener.model.Link;
import com.example.links_shortener.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

public class RedirectController {

    @Bean
    private LinkService redirectLinkService() {
        return new LinkService();
    }

    @Autowired
    private LinkService linkService;


    @GetMapping("/{shortUrl}")
    @ResponseBody
    public String findOneLink(@PathVariable String shortUrl, HttpServletResponse response) {

        if (shortUrl.length() < 5) {
            response.setStatus( HttpServletResponse.SC_NOT_FOUND);
            return "link not found";
        }

        Link link = linkService.findByShortUrl(shortUrl);

        if (link != null) {
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            link.setClicks();
            linkService.save(link);
            response.setHeader("Location", link.getLongUrl());
            return "go to the long url";
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "link not found";
        }
    }
}
