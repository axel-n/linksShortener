package com.example.links_shortener;

import com.example.links_shortener.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    @Autowired
    private LinkRepository linkRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}



