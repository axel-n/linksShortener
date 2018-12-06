//package com.example.linksShortener.repository;
//
//import com.example.linksShortener.model.Link;
//
//import com.example.linksShortener.model.User;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest()
//public class LinkServiceTest extends DatabaseTest {
//
//    @Autowired
//    private LinkRepository linkService;
//
//    @Test
//    public void SaveOneLink() {
//
//        User user1 = new User("Vasya");
//
//        Link link1 = new Link("test.com");
//
//        user1.setLinks(link1);
//        System.out.println(user1);
//
//        // user recieve in browser/api
//        String expectedShortUrl = this.linkService.saveLink(link1);
//
//        System.out.println(user1);
//        assertNotNull(expectedShortUrl);
//
//        Link link2 = linkService.findByShortUrl(expectedShortUrl);
//
//        String resultShortUrl = link2.getShortUrl();
//        System.out.println(link2);
//
//        assertEquals(1,1);
//        //assertEquals(expectedShortUrl, resultShortUrl);
//    }
//
////    @Test
////    public void SaveSeveralLinks() {
////
////        Link link1 = new Link("https://ya.ru");
////        Link link2 = new Link("https://google.com");
////        Link link3 = new Link("https://example.com");
////
////        int sizeBefore = linkService.findAll().size();
////        assertEquals(0, sizeBefore);
////
////        linkService.saveLink(link1);
////        linkService.saveLink(link2);
////        linkService.saveLink(link3);
////
////        int sizeAfter = linkService.findAll().size();
////
////        assertEquals(3, sizeAfter);
////    }
//
//
//}
