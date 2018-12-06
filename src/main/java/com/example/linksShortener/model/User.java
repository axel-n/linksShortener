package com.example.linksShortener.model;


import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Formatter;

@Entity
@NamedQuery(name = "User.findByUserName", query = "from User l where l.name = ?1")
@Table(name = "users")
public class User extends AbstractPersistable<Integer> {

    private String name;

    @Transient
    private ArrayList<Link> links;

    // default constructor for spring. don't remove
    public User() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.links = new ArrayList<>();
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    public void setLinks(Link link) {
        this.links.add(link);
    }

    @Override
    public String toString() {

        Formatter formatter = new Formatter();
        formatter.format("%n%n=================================");

        formatter.format("%nname: %s, id: %s", name, getId());
        formatter.format("%n----------------------");
        formatter.format("%nLinks:");

        for (int i = 0; i < links.size(); i++) {
            formatter.format("%s", links.get(i));
        }

        formatter.format("%n----------------------");

        return formatter.toString();
    }
}

