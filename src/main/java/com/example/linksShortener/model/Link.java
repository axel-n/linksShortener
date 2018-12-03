package com.example.linksShortener.model;

import java.sql.Timestamp;

public class Link {

    private String shortUrl;
    private String longUrl;
    private Timestamp createTime = new Timestamp(System.currentTimeMillis());
    private Statistic statistic = new Statistic();

    public Link() {}

    public Link(String shortUrl, String longUrl) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return
                shortUrl + " " +
                longUrl + " " +
                createTime + " " +
                statistic.getClicks() + " " +
                statistic.getIp();
    }
}