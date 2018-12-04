package com.example.linksShortener.repository;

import com.example.linksShortener.model.Link;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


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