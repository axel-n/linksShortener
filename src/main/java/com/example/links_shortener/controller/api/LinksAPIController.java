package com.example.links_shortener.controller.api;


import com.example.links_shortener.model.Link;
import com.example.links_shortener.repository.LinkRepository;
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

        return linkRepository.findAll();
    }

    @GetMapping("/{shortUrl}")
    public String findOneLink(@PathVariable String shortUrl) {

        Link link = linkRepository.findByShortUrl(shortUrl);

        if (link != null) {

            link.setClicks();
            linkRepository.save(link);

            return link.getLongUrl();

        } else {
            // TODO
            // переделать в нормальные исключения
            return shortUrl + " URL not Found";
        }

    }
}


