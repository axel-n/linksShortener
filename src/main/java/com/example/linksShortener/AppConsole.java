package com.example.linksShortener;

import com.example.linksShortener.model.Link;
import com.example.linksShortener.model.User;

public class AppConsole {

    public static void main(String[] args) {

        User user1 = new User("Vasya");

        user1.setLinks(new Link("test1.com"));
        user1.setLinks(new Link("test2.com"));

        System.out.println(user1);

        for (int i = 0; i < user1.getLinks().size(); i++) {
            user1.getLinks().get(i).addStatistic("abc");
            user1.getLinks().get(i).addStatistic("bcd");
            user1.getLinks().get(i).addStatistic("abc");
        }

        System.out.println(user1);
    }
}
