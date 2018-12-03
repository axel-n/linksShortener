package com.example.linksShortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

//        Link link1 = new Link("abcde", "https://ya.ru");
//        int result = linkService.saveLink(link1);
//        System.out.println(result);
    }
}


