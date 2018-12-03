package com.example.linksShortener.repository;

import com.example.linksShortener.model.Link;
import java.util.List;

public interface ILinkRepository {

    public List<Link> findAll();
    public Link findByShortUrl(String shortUrl);
    public int saveLink(Link link);

}