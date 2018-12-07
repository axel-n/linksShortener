package com.example.links_shortener.repository;

import com.example.links_shortener.model.Link;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface LinkRepository extends CrudRepository<Link, Integer>{

    Link findByShortUrl(String shortUrl);
    List<Link> findByUserId (int userId);
    List<Link> findAll();
}