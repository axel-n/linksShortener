package com.example.links_shortener.core.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private String username;
    private String password;
    private String email;
    private boolean enabled;
    private String role;

    @Transient
    private final String DEFAULT_ROLE = "ROLE_USER";

    // default constructor for spring. don't remove
    public User() {
        this.role = DEFAULT_ROLE;
    }

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRole() {
        return role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("{id: %s,username: %s, password: %s, email: %s}",
                id, username, password, email);
    }
}

