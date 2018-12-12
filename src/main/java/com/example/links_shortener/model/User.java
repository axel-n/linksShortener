package com.example.links_shortener.model;


import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistence.*;
import java.util.Formatter;

@Entity
@Table(name = "users")
public class User extends AbstractPersistable<Integer> {

    private String name;

    // default constructor for spring. don't remove
    public User() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        Formatter formatter = new Formatter();
        formatter.format("%n%n=================================");
        formatter.format("%nname: %s, id: %s", name, getId());

        return formatter.toString();
    }
}

