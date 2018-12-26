package com.example.links_shortener;

import com.example.links_shortener.core.model.Link;

import com.example.links_shortener.core.model.User;
import com.example.links_shortener.core.service.LinkService;
import com.example.links_shortener.core.service.UserService;
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
    private static final String TEST_USER_NAME = "Username";

    @Autowired
    private LinkService linkService;

    @Autowired
    private UserService userService;

    @Test
    public void SaveOneLinkGuest() {

        Link link1 = new Link(TEST_URL1);

        // receive user from browser/api (before save in bd)
        String resultShortUrl = link1.getShortUrl();
        assertNotNull(resultShortUrl);

        // receive user from browser/api (after save in bd)
        String expectedShortUrl = linkService.save(link1).getShortUrl();
        assertNotNull(expectedShortUrl);
        assertEquals(expectedShortUrl, resultShortUrl);

        // receive user from browser/api (search in bd)
        String resultShortUrl2 = linkService.findByShortUrl(resultShortUrl).getShortUrl();
        assertEquals(expectedShortUrl, resultShortUrl2);
    }

    @Test
    public void SaveLinkForAuthorizedUser() {

        User user1 = new User();
        user1.setUsername(TEST_USER_NAME);
        int userId =  userService.save(user1).getId();

        // check empty links for new user
        int countLinkByUserBefore = linkService.findByUserId(userId).size();
        assertEquals(0, countLinkByUserBefore);

        Link link1 = new Link(TEST_URL1);
        link1.setUserId(userId);
        String shortUrl1 = link1.getShortUrl();

        // receive user from browser/api (after save in bd)
        Link savedLink = linkService.save(link1);

        assertEquals(userId, savedLink.getUserId());
        assertEquals(shortUrl1, savedLink.getShortUrl());
        assertEquals(TEST_URL1, savedLink.getLongUrl());

        int countLinkByUserAfter = linkService.findByUserId(userId).size();
        assertEquals(1, countLinkByUserAfter);

    }

}
