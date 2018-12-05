package com.example.linksShortener.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Formatter;
import java.util.Random;


public class Link {

    public Link(String longUrl) {
        this.shortUrl = generateShortUrl();
        this.LongUrl = longUrl;
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.statistic = new Statistic();
    }

    public String getLongUrl() {
        return LongUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getCreateTime() {
        return new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(createTime);
    }

    @Override
    public String toString() {
        Formatter formatter = new Formatter();

        formatter.format("\n\nshortUrl: %s, longUrl: %s", getShortUrl(), getLongUrl());
        formatter.format("\nstatistic: %s", getStatistic());

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

    public Statistic getStatistic() {
        return statistic;
    }

    public void addStatistic(String ip) {
        getStatistic().incClicks();
        getStatistic().addIp(ip);
    }

    private static final int MAX_COUNT_WORDS = 5;
    private final String POSSIBLE_CHARACTERS = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890";

    private String shortUrl;
    private String LongUrl;
    private Statistic statistic;
    private Timestamp createTime;

}
