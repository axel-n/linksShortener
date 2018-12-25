package com.example.links_shortener.config;

public enum TypeMessage {

    // https://getbootstrap.com/docs/4.1/components/alerts/

    WARNING,
    INFO,
    PRIMARY,
    SECONDARY,
    SUCCESS,
    DANGER,
    LIGHT,
    DARK;


    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
