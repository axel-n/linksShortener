package com.example.linksShortener.model;


import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Formatter;
import java.util.Random;

@Entity
@NamedQuery(name = "Link.findByShortUrl", query = "from Link l where l.shortUrl = ?1")
@Table(name = "links")
public class Link extends AbstractPersistable<Integer> {

    private int userId;
    @Column(unique = true)
    private String shortUrl;
    private String longUrl;
    private String statistic;
    private String createTime;

    @Transient
    private static final int MAX_COUNT_WORDS = 5;
    @Transient
    private final String POSSIBLE_CHARACTERS = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890";
    @Transient
    private static final int DEFAULT_USER_ID = 0;

    @Transient
    // чтобы spring не пыталась создать колонку с "неизвестным" типом, но мы имели доступ к полям без обработки через json
    private Statistic statisticHidden;

    // default constructor for spring. don't remove
    public Link() {
    }

    public String setLongUrl(String longUrl) {
        return setLongUrl(longUrl, DEFAULT_USER_ID);
    }

    public String setLongUrl(String longUrl, int userId) {
        this.userId = userId;
        this.longUrl = longUrl;
        this.shortUrl = generateShortUrl();
        this.statisticHidden = new Statistic();
        this.statistic = statisticHidden.getData2Json(); // немного магии :)
        this.createTime = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(new Timestamp(System.currentTimeMillis()));
        return this.shortUrl;
    }



    public String getLongUrl() {
        return longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getStatistic() {
        return statistic;
    }

    public void addStatistic(String ip) {
        statisticHidden.incClicks();
        statisticHidden.addIp(ip);
    }

    @Override
    public String toString() {
        Formatter formatter = new Formatter();

        formatter.format("%n%nshortUrl: %s, longUrl: %s, created: %s", shortUrl, longUrl, getCreateTime());
        formatter.format("%nstatistic: %s", statistic);

        return formatter.toString();
    }


    private String generateShortUrl() {
        StringBuilder idBuilder = new StringBuilder();
        Random rnd = new Random();
        while (idBuilder.length() < MAX_COUNT_WORDS) {
            int index = (int) (rnd.nextFloat() * POSSIBLE_CHARACTERS.length());
            idBuilder.append(POSSIBLE_CHARACTERS.charAt(index));
        }
        return idBuilder.toString();
    }



}
