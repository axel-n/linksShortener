package com.example.linksShortener.model;


import java.util.ArrayList;
import java.util.Formatter;
import java.util.UUID;

public class User {

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Link> getLinks() {
        return link;
    }

    public void setLinks(Link link) {
        this.link.add(link);
    }

    public User(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.link = new ArrayList<>();
    }

    @Override
    public String toString() {

        Formatter formatter = new Formatter();
        formatter.format("\n\n=================================");

        formatter.format("\nname: %s, UUID: %s", getName(), getId());
        formatter.format("\n----------------------");
        formatter.format("\nLinks:");

        for (int i = 0; i < link.size(); i++) {
            formatter.format("%s", link.get(i));
        }

        formatter.format("\n----------------------");

        return formatter.toString();
    }

    private UUID id;
    private String name;
    private ArrayList<Link> link;
}

