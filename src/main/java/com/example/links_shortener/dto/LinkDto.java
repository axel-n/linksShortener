package com.example.links_shortener.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LinkDto {

    @NotNull
    @NotEmpty
    private String longUrl;


    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
