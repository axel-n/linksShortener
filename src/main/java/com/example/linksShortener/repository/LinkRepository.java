package com.example.linksShortener.repository;


import com.example.linksShortener.model.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

@Service
public class LinkRepository implements ILinkRepository {

    @Autowired
    private JdbcTemplate jtm;

    public List<Link> findAll() {
        String sql = "SELECT * FROM LINKS";
        List<Link> links = jtm.query(sql, new BeanPropertyRowMapper(Link.class));
        return links;
    }

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

    public int saveLink(Link link) {

        JSONObject statisticksJson = new JSONObject();

        statisticksJson.put("clicks", link.getClicks());
        statisticksJson.put("ips", link.getIp());

        return jtm.update("insert into LINKS (shortURL, longURL, createTime, statistics) " + "values(?, ?, ?, ?)",
                link.getShortUrl(), link.getLongUrl(), link.getCreateTime(), statisticksJson.toJSONString());
    }


}