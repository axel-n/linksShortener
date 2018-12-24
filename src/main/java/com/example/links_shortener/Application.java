package com.example.links_shortener;

import com.example.links_shortener.service.LinkService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Bean
    public LinkService linkService() {
        return new LinkService();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}



