package com.example.links_shortener.dao;

import com.example.links_shortener.model.Link;

import com.example.links_shortener.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class LinkServiceTest extends DatabaseTest {

    private static final String TEST_URL1 = "test1.com";
    private static final String TEST_URL2 = "test2.com";
    private static final String TEST_USER_NAME = "Vasya";

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void SaveOneLinkGuest() {

        Link link1 = new Link();

        // receive user from browser/api (before save in bd)
        String resultShortUrl = link1.setLongUrl(TEST_URL1);
        assertNotNull(resultShortUrl);

        // receive user from browser/api (after save in bd)
        String expectedShortUrl = linkRepository.save(link1).getShortUrl();
        assertNotNull(expectedShortUrl);
        assertEquals(expectedShortUrl, resultShortUrl);

        // receive user from browser/api (search in bd)
        String resultShortUrl2 = linkRepository.findByShortUrl(resultShortUrl).getShortUrl();
        assertEquals(expectedShortUrl, resultShortUrl2);
    }

    @Test
    public void SaveSeveralLinksGuest() {

        int sizeBeforeSave = linkRepository.findAll().size();
        assertEquals(0, sizeBeforeSave);

        Link link1 = new Link();
        Link link2 = new Link();

        // receive user from browser/api (before save in bd)
        link1.setLongUrl(TEST_URL1);
        link2.setLongUrl(TEST_URL2);

        // receive user from browser/api (after save in bd)
        linkRepository.save(link1);
        linkRepository.save(link2);

        int sizeAfterSave = linkRepository.findAll().size();
        assertEquals(2, sizeAfterSave);

    }

    @Test
    public void SaveLinkForAuthorizedUser() {

        User user1 = new User();
        user1.setUsername(TEST_USER_NAME);
        int userId =  userRepository.save(user1).getId();

        // check empty links for new user
        int countLinkByUserBefore = linkRepository.findByUserId(userId).size();
        assertEquals(0, countLinkByUserBefore);

        Link link1 = new Link();

        // receive user from browser/api (before save in bd)
        String shortUrl1 = link1.setLongUrl(TEST_URL1, userId);

        // receive user from browser/api (after save in bd)
        Link savedLink = linkRepository.save(link1);

        assertEquals(userId, savedLink.getUserId());
        assertEquals(shortUrl1, savedLink.getShortUrl());
        assertEquals(TEST_URL1, savedLink.getLongUrl());

        int countLinkByUserAfter = linkRepository.findByUserId(userId).size();
        assertEquals(1, countLinkByUserAfter);

    }

}
