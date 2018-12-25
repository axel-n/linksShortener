package com.example.links_shortener;

import org.junit.Before;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;


public class DatabaseTest {

    private final String TRUNCATE_LINKS = "TRUNCATE TABLE LINKS";
    private final String TRUNCATE_USERS = "TRUNCATE TABLE USERS";

    @Autowired
    private JdbcTemplate jtm;

    @Before
    public void deleteAll() {
        jtm.execute(TRUNCATE_LINKS);
        jtm.execute(TRUNCATE_USERS);

        System.out.println("Truncate tables!");
    }
}