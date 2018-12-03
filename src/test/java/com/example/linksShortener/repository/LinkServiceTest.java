package com.example.linksShortener.repository;

import com.example.linksShortener.model.Link;
import org.junit.Test;

public class LinkServiceTest  {

    private ILinkRepository linkService;

    @Test
    public void testSaveLink() {

        Link link1 = new Link("abcd", "https://ya.ru");
        int result = linkService.saveLink(link1);

        System.out.println(result);
    }
}
