package com.example.linksShortener.service;

import com.example.linksShortener.bean.Link;
import org.springframework.beans.factory.annotation.Autowired;
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

        String sql = "SELECT * FROM LINKS WHERE shortUrl=?";

        Link link = (Link) jtm.queryForObject(sql, new Object[]{shortUrl},
                new BeanPropertyRowMapper(Link.class));

        return link;
    }
}