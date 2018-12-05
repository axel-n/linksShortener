package com.example.linksShortener.repository;


import com.example.linksShortener.model.Link;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class LinkRepository implements ILinkRepository {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LinkRepository.class);


    private final String ALL_LINKS = "SELECT * FROM LINKS";
    private final String LINKS_BY_SHORT_URL = "SELECT * FROM LINKS WHERE shortUrl= ?";
    private final String LINKS_ADD_NEW = "INSERT INTO LINKS (shortURL, longURL, createTime, statistics) VALUES (?, ?, ?, ?)";
    private final String LINKS_UPDATE_STATISTICS = "UPDATE LINKS SET statistics = ? where shortUrl = ?";

    @Autowired
    private JdbcTemplate jtm;

    public List<Link> findAll() {
        return jtm.query(ALL_LINKS, new BeanPropertyRowMapper(Link.class));
    }

    public Link findByShortUrl(String shortUrl) {

        try {
            return (Link) jtm.queryForObject(LINKS_BY_SHORT_URL, new Object[]{shortUrl}, new BeanPropertyRowMapper(Link.class));
        } catch (EmptyResultDataAccessException e) {

            log.info("{} not found", shortUrl);
        }
        return null;
    }

    public String saveLink(Link link) {

        String statisticJson = link.getStatistic().getStatistic2Json();

        // TODO
        // добавить несколько попыток
        String shortURl = link.getShortUrl();

        log.info("shortUrl {} with data saving  in database", shortURl);

        jtm.update(LINKS_ADD_NEW,
                shortURl, link.getLongUrl(), link.getCreateTime(), statisticJson);

        return shortURl;
    }

    public void updateStatistic(Link link) {

        String statisticJson = link.getStatistic().getStatistic2Json();

        log.info("save updated statistic {} for shortUrl {}", statisticJson, link.getShortUrl());

        jtm.update(LINKS_UPDATE_STATISTICS, statisticJson, link.getShortUrl());
    }




}