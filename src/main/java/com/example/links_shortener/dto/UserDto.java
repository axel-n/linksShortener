package com.example.links_shortener.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
//import com.example.links_shortener.controller.security.SecurityController.ValidEmail;
//import com.example.links_shortener.controller.security.SecurityController.PasswordMatches;

import java.util.Formatter;

/**
* We need a Data Transfer Object to send all of the registration
* information to our Spring backend. The DTO object should have all
* the information we’ll require later on when we create
* and populate our User object
* */

// @PasswordMatches
public class UserDto {
    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

    //@ValidEmail
    @NotNull
    @NotEmpty
    private String email;

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

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    @Override
    public String toString() {
        Formatter formatter = new Formatter();
        formatter.format("%n username: %s, password: %s, matchingPassword: %s, email: %s",
                username, password, matchingPassword, email);

        return formatter.toString();
    }
}

