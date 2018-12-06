package com.example.linksShortener.controller.api;


import com.example.linksShortener.model.Link;
import com.example.linksShortener.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinksController {

    @Autowired
    private LinkRepository linkRepository;

    @RequestMapping("${spring.data.rest.base-path}/links")
    public Iterable<Link> findLinks() {

        Iterable<Link> links = linkRepository.findAll();

        System.out.println(links.toString());

        return linkRepository.findAll();
    }

    //    @GetMapping("/{shortUrl}")
//    public String findLink(@PathVariable String shortUrl) {
//
//        Link_ link = linkRepository.findByShortUrl(shortUrl);
//
//        if (link != null) {
//
//            link.incrimentClicks();
//
//            linkRepository.updateStatistic(link);
//            return linkRepository.findByShortUrl(shortUrl).getLongUrl();
//
//        } else {
//            // TODO
//            // переделать в нормальные исключения
//            return shortUrl + " URL not Found";
//        }

}


