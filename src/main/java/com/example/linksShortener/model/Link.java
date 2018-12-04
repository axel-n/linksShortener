package com.example.linksShortener.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Link {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    private String shortUrl;
    private String longUrl;
    private Timestamp createTime;

    // TODO
    // move statistic data to another class
    private int clicks;
    private List<String> ip;

    public Link() {}

    public Link(String longUrl) {
        this.shortUrl = "";
        this.longUrl = longUrl;
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.clicks = 0;
        this.ip = new ArrayList<>();
    }

    public String getLongUrl() {
        return this.longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return this.shortUrl;
    }

    public String getCreateTime() {
        return new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(createTime);
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public int getClicks() {
        return clicks;
    }

    public void incrimentClicks() {
        this.clicks++;
    }

    public List<String> getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip.add(ip);
    }

    @Override
    public String toString() {
        return
                shortUrl + " " +
                longUrl + " " +
                createTime + " " +
                clicks + " " +
                ip;
    }
}