package com.example.linksShortener.repository;

import com.example.linksShortener.model.Link;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface LinkRepository extends CrudRepository<Link, Integer>{

    Link findByShortUrl(String shortUrl);
}