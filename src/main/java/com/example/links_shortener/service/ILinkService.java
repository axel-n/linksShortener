package com.example.links_shortener.service;

import com.example.links_shortener.model.Link;
import org.springframework.security.core.Authentication;

public interface ILinkService {

    Link addLink(String longUrl, Authentication authentication);
}
