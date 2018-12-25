package com.example.links_shortener.controller.security;

import com.example.links_shortener.config.TypeMessage;

import java.util.HashMap;

public class Message {

    private HashMap<String, String> message;
    private final String KEY_TYPE = "type";
    private final String KEY_VALUE = "text";

    public Message(TypeMessage type, String value) {
        this.message = new HashMap<>();
        this.message.put(KEY_TYPE, type.toString());
        this.message.put(KEY_VALUE, value);
    }

    public HashMap<String, String> getMessage() {
        return this.message;
    }



}
