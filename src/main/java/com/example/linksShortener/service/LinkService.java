package com.example.linksShortener.service;


import com.example.linksShortener.model.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkService implements ILinkService {

    @Autowired
    private JdbcTemplate jtm;

    @Override
    public List<Link> findAll() {

        String sql = "SELECT * FROM LINKS";

        List<Link> links = jtm.query(sql, new BeanPropertyRowMapper(Link.class));

        System.out.println(links);

        return links;
    }

    @Override
    public Link findByShortUrl(String shortUrl) {

        String sql = "SELECT * FROM LINKS WHERE shortUrl= ?";

        try {
            Link link = (Link) jtm.queryForObject(sql, new Object[]{shortUrl}, new BeanPropertyRowMapper(Link.class));
            return link;
        } catch (EmptyResultDataAccessException e) {
            System.out.println(shortUrl + "not found");
        }

        return null;
    }
}