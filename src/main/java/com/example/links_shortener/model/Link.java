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
    private String created;

    @Transient
    private static final int MAX_COUNT_WORDS = 5;
    @Transient
    private final String POSSIBLE_CHARACTERS = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890";
    @Transient
    private static final int DEFAULT_USER_ID = 0;

    // default constructor for spring. don't remove
    public Link() {
    }

    public Link(String longUrl) {
        this.longUrl = longUrl;
        this.shortUrl = generateShortUrl();
        this.created = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Timestamp(System.currentTimeMillis()));
        this.userId = DEFAULT_USER_ID;
    }


    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
       this.userId = userId;
    }

    public String getLongUrl() {
        return this.longUrl;
    }

    public String getShortUrl() {
        return this.shortUrl;
    }

    public String getCreated() {
        return this.created;
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
        formatter.format("Link {id: %s, shortUrl: %s, longUrl: %s, created: %s, userId: %s, clicks: %s}", getId(), shortUrl, longUrl, created, userId, clicks);
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
