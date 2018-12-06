package com.example.linksShortener.controller.api;


import com.example.linksShortener.model.Link;
import com.example.linksShortener.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinksAPIController {

    @Autowired
    private LinkRepository linkRepository;

    @RequestMapping("${spring.data.rest.base-path}/links")
    public Iterable<Link> findAllLinks() {

        Iterable<Link> links = linkRepository.findAll();

        System.out.println(links.toString());

        return linkRepository.findAll();
    }

    @GetMapping("/{shortUrl}")
    public String findOneLink(@PathVariable String shortUrl) {

        Link link = linkRepository.findByShortUrl(shortUrl);

        if (link != null) {

            link.addStatistic("some ip");
            linkRepository.save(link);

            return link.getLongUrl();

        } else {
            // TODO
            // переделать в нормальные исключения
            return shortUrl + " URL not Found";
        }

    }
}


