package com.example.links_shortener.service;

import com.example.links_shortener.model.Link;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ILinkService {

    Link addLink(String longUrl, Authentication authentication);

    Link findByShortUrl(String shortUrl);
    List<Link> findByUserId (int userId);
    List<Link> findAll();
    Link save(Link link);
}
