package com.example.linksShortener.model;

import java.util.ArrayList;
import java.util.List;

public class Statistic {

    private int clicks;
    private List<String> ip;

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

    public Statistic() {
        this.clicks = 0;
        this.ip = new ArrayList<>();
    }

}
