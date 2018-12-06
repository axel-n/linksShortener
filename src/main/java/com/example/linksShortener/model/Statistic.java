package com.example.linksShortener.model;


import org.json.simple.JSONObject;

import java.util.Formatter;
import java.util.TreeSet;

public class Statistic {

    private int clicks;
    private TreeSet<String> ip;

    public Statistic() {
        this.clicks = 0;
        this.ip = new TreeSet<>();
    }

    public int getClicks() {
        return clicks;
    }

    public void incClicks() {
        this.clicks++;
    }

    public TreeSet<String> getIp() {
        return ip;
    }

    public void addIp(String ip) {
        this.ip.add(ip);
    }

    public String getData2Json() {
        JSONObject statisticksJson = new JSONObject();

        statisticksJson.put("clicks", clicks);
        statisticksJson.put("ips", ip);

        return statisticksJson.toJSONString();
    }

    @Override
    public String toString() {
        Formatter formatter = new Formatter();

        formatter.format("clicks: %s, list ip: %s", getClicks(), getIp());
        return formatter.toString();
    }
}
