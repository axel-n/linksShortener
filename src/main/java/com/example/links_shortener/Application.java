package com.example.links_shortener;

import com.example.links_shortener.core.service.LinkServiceImp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Bean
    public LinkServiceImp linkService() {
        return new LinkServiceImp();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}



