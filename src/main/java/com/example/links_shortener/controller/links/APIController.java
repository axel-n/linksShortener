package com.example.links_shortener.controller.links;

import com.example.links_shortener.dto.LinkDto;
import com.example.links_shortener.model.Link;
import com.example.links_shortener.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class APIController {

    private static final String templateForGuest = "{\"shortUrl\": \"%s\", \"longUrl\": \"%s\"}";

    @Autowired
    private LinkService linkService;

    @PostMapping(value = "${spring.data.rest.base-path}/link", produces = MediaType.APPLICATION_JSON_VALUE)
    public String addLink(@Valid final LinkDto linkDto, Authentication authentication) {

        Link link = linkService.addLink(linkDto.getLongUrl(), authentication);

        if (authentication == null) {
            return String.format(templateForGuest, link.getShortUrl(), link.getLongUrl());
        }

        return link.toString();
    }

    @GetMapping(value = "${spring.data.rest.base-path}/link/{shortUrl}")
    public Link viewLink(@PathVariable String shortUrl, Authentication authentication) {

        return linkService.findByShortUrl(shortUrl);
    }

}


