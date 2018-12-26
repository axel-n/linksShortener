package com.example.links_shortener.core.service;

import com.example.links_shortener.core.model.Link;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ILinkService {

    Link addLink(String longUrl, Authentication authentication);

    Link findByShortUrl(String shortUrl);
    List<Link> findByUserId (int userId);
    Link save(Link link);
}
