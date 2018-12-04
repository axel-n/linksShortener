package com.example.linksShortener.controller.api;

import com.example.linksShortener.model.Link;
import com.example.linksShortener.repository.ILinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LinksController {

    @Autowired
    private ILinkRepository linkRepository;

    @RequestMapping("${spring.data.rest.base-path}/links")
    public List<Link> findLinks() {

        return linkRepository.findAll();
    }

    // @RequestMapping("/{shortUrl}")
    @GetMapping("/{shortUrl}")
    public String findLink(@PathVariable String shortUrl) {

        Link link = linkRepository.findByShortUrl(shortUrl);

        if (link != null) {

            link.incrimentClicks();
//            link.setIp("new ip");

            linkRepository.updateStatistic(link);
            return linkRepository.findByShortUrl(shortUrl).getLongUrl();


        } else {
            // TODO
            // переделать в нормальные исключения
            return shortUrl + " URL not Found";
        }

    }
}