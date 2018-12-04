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
import java.util.Random;

@Service
public class LinkRepository implements ILinkRepository {

    private final int MAX_COUNT_WORDS = 5;
    private final String ALL_LINKS = "SELECT * FROM LINKS";
    private final String LINKS_BY_SHORT_URL = "SELECT * FROM LINKS WHERE shortUrl= ?";
    private final String LINKS_ADD_NEW = "INSERT INTO LINKS (shortURL, longURL, createTime, statistics) VALUES (?, ?, ?, ?)";
    private final String LINKS_UPDATE_STATISTICS = "UPDATE LINKS SET statistics = ?";

    private final String POSSIBLE_CHARACTERS = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890";

    @Autowired
    private JdbcTemplate jtm;

    public List<Link> findAll() {
        List<Link> links = jtm.query(ALL_LINKS, new BeanPropertyRowMapper(Link.class));
        System.out.println(links);
        return links;
    }

    public Link findByShortUrl(String shortUrl) {

        try {
            Link link = (Link) jtm.queryForObject(LINKS_BY_SHORT_URL, new Object[]{shortUrl}, new BeanPropertyRowMapper(Link.class));
            return link;
        } catch (EmptyResultDataAccessException e) {
            System.out.println(shortUrl + "not found");
        }

        return null;
    }

    public int saveLink(Link link) {

        String statisticJson = getStatistic2Json(link);

        // TODO
        // добавить несколько попыток
        String shortURl = getRandomShortUrl();

        return jtm.update(LINKS_ADD_NEW,
                shortURl, link.getLongUrl(), link.getCreateTime(), statisticJson);
    }

    private String getRandomShortUrl() {

        StringBuilder idBuilder = new StringBuilder();
        Random rnd = new Random();
        while (idBuilder.length() < MAX_COUNT_WORDS ) {
            int index = (int) (rnd.nextFloat() * POSSIBLE_CHARACTERS.length());
            idBuilder.append(POSSIBLE_CHARACTERS.charAt(index));
        }
        return idBuilder.toString();
    }

    public void updateStatistic(Link link) {

        String statisticJson = getStatistic2Json(link);

        System.out.println("save updated statistic " +  statisticJson +
                " for shortUrl " + link.getShortUrl());

        jtm.update(LINKS_UPDATE_STATISTICS, statisticJson);
    }

    private String getStatistic2Json(Link link) {
        JSONObject statisticksJson = new JSONObject();

        statisticksJson.put("clicks", link.getClicks());
        statisticksJson.put("ips", link.getIp());

        return statisticksJson.toJSONString();
    }


}