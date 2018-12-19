package com.example.links_shortener.dao;

import com.example.links_shortener.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Integer> {

    Link findByShortUrl(String shortUrl);
    List<Link> findByUserId (int userId);
    List<Link> findAll();
}