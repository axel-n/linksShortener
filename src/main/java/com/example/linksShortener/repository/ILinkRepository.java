package com.example.linksShortener.repository;

import com.example.linksShortener.model.Link;
import java.util.List;

public interface ILinkRepository {

    public List findAll();
    public Link findByShortUrl(String shortUrl);
    public String saveLink(Link link);
    public void updateStatistic(Link link);
}