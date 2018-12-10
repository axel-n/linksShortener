package com.example.links_shortener.model;


import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Formatter;
import java.util.Random;

@Entity
@Table(name = "links")
public class Link extends AbstractPersistable<Integer> {

    private int userId;

    @Column(unique = true, name = "short_url")
    private String shortUrl;

    @Column(name = "long_url")
    private String longUrl;

    private int clicks;

    @Column(name = "created")
    private String createTime;

    @Transient
    private static final int MAX_COUNT_WORDS = 5;
    @Transient
    private final String POSSIBLE_CHARACTERS = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890";
    @Transient
    private static final int DEFAULT_USER_ID = 0;

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
        this.createTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Timestamp(System.currentTimeMillis()));

        return this.shortUrl;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getLongUrl() {
        return this.longUrl;
    }

    public String getShortUrl() {
        return this.shortUrl;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public int getClicks() {
        return this.clicks;
    }

    public void setClicks() {
        this.clicks++;
    }

    @Override
    public String toString() {
        Formatter formatter = new Formatter();

        formatter.format("%n%nshortUrl: %s, longUrl: %s, created: %s, clicks: %s", shortUrl, longUrl, createTime, clicks);

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
