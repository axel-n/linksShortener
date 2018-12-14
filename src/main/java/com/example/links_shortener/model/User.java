package com.example.links_shortener.model;


import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;

@Entity
@Table(name = "users")
public class User extends AbstractPersistable<Integer> {

    private String username;
    private String password;
    private String email;

    // default constructor for spring. don't remove
    public User() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {

        Formatter formatter = new Formatter();
        formatter.format("%nid: %s,username: %s, password: %s, email: %s",
                getId(), username, password, email);

        return formatter.toString();
    }


}

