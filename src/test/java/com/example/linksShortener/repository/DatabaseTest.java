package com.example.linksShortener.repository;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


public class DatabaseTest {

    private final String TRUNCATE = "TRUNCATE TABLE LINKS";

    @Autowired
    private JdbcTemplate jtm;

    @Before
    public void deleteAll() {
        System.out.println("Truncate table!");
        jtm.execute(TRUNCATE);
    }
}