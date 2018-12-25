package com.example.links_shortener.service;

import com.example.links_shortener.dao.LinkRepository;
import com.example.links_shortener.dao.UserRepository;
import com.example.links_shortener.model.Link;
import com.example.links_shortener.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import java.util.List;

public class LinkService implements ILinkService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private UserRepository userRepository;

    public Link addLink(String longUrl, Authentication authentication) {
        Link link = new Link(longUrl);

        if (authentication != null) {
            User loggedUser = userRepository.findByEmail(authentication.getName());
            link.setUserId(loggedUser.getId());
        }

        link = linkRepository.save(link);
        log.info("save {}", link);

        return link;
    }

    public Link findByShortUrl(String shortUrl) {
        return linkRepository.findByShortUrl(shortUrl);
    }

    public List<Link> findByUserId(int userId) {
        return linkRepository.findByUserId(userId);
    }

    public List<Link> findAll(){
        return linkRepository.findAll();
    }

    public Link save(Link link) {
        return linkRepository.save(link);
    }
}
