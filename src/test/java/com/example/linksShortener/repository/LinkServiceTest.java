package com.example.linksShortener.repository;

import com.example.linksShortener.Application;
import com.example.linksShortener.model.Link;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class LinkServiceTest  {

    @Autowired
    private ILinkRepository linkService;

    @Test
    public void SaveOneLink() {

        Link link1 = new Link("https://ya.ru");
        String expectedShortUrl = linkService.saveLink(link1);

        String resultShortUrl = linkService.findByShortUrl(expectedShortUrl).getShortUrl();

        assertEquals(expectedShortUrl, resultShortUrl);
    }

    @Test
    public void SaveSeveralLinks() {

        Link link1 = new Link("https://ya.ru");
        Link link2 = new Link("https://google.com");
        Link link3 = new Link("https://example.com");

        int sizeBefore = linkService.findAll().size();
        assertEquals(0, sizeBefore);

        linkService.saveLink(link1);
        linkService.saveLink(link2);
        linkService.saveLink(link3);

        int sizeAfter = linkService.findAll().size();

        assertEquals(3, sizeAfter);
    }
}
