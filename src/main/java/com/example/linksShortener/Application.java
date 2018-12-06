package com.example.linksShortener;

import com.example.linksShortener.model.Link;
import com.example.linksShortener.model.User;
import com.example.linksShortener.repository.LinkRepository;
import com.example.linksShortener.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(LinkRepository linkRepository, UserRepository userRepository) {
        return (args) -> {

            ////////////////////////
            //guest
            ////////////////////////
            Link link1 = new Link();

            // receive user from browser/api (before save in bd)
            String shortUrl1 = link1.setLongUrl("test.com");

            // receive user from browser/api (after save in bd)
            String shortUrl2 = linkRepository.save(link1).getShortUrl();

            // receive user from browser/api (search in bd)
            String shortUrl3 = linkRepository.findByShortUrl(shortUrl1).getShortUrl();

            // должно быть
            // shortUrl1 == shortUrl2 == shortUrl3


            ////////////////////////
            // authorized user
            ////////////////////////
            User user1 = new User();

            user1.setName("Vasya");
            int userId = userRepository.save(user1).getId();

            System.out.println(user1);
            Link link2 = new Link();

            // receive user from browser/api (before save in bd)
            shortUrl1 = link2.setLongUrl("test2.com", userId);

            // receive user from browser/api (after save in bd)
            shortUrl2 = linkRepository.save(link2).getShortUrl();

            //System.out.println(user1);


        };


    }
}


