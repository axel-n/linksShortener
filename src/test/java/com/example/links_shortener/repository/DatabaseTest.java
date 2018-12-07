package com.example.links_shortener.repository;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


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