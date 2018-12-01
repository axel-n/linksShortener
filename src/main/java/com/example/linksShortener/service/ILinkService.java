package com.example.linksShortener.service;

import com.example.linksShortener.bean.Link;
import java.util.List;

public interface ILinkService {

    public List<Link> findAll();
    public Link findByShortUrl(String shortUrl);
}