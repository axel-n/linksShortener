//package com.example.linksShortener.repository;
//
//import com.example.linksShortener.model.Link;
//import com.example.linksShortener.model.Statistic;
//import com.example.linksShortener.model.User;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest()
//@AutoConfigureMockMvc
//public class WebControllerTest extends DatabaseTest {
//
//    @Autowired
//    private LinkRepository linkRepository;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void openLink() throws Exception {
//
//        //User user1 = new User("Vasya");
//
//        Link link1 = new Link("test.com");
//        System.out.println(link1);
//
//        // user recieve in browser/api
//        String shortUrl = this.linkRepository.saveLink(link1);
//
//        Link link = this.linkRepository.findByShortUrl(shortUrl);
//
//
//        Statistic statistic = link.getStatistic();
//
//
//
//        int clickBeforeOpen  = statistic.getClicks();
//        assertEquals(0, clickBeforeOpen);
//
//        // goto to longUrl by shortUrl
//        this.mockMvc.perform(get("/" + shortUrl)).andDo(print()).andExpect(status().isOk());
//
//        // and backend increment statistic clicks
//        int clickAfterOpen = linkRepository.findByShortUrl(shortUrl).getStatistic().getClicks();
//        assertEquals(1, clickAfterOpen);
//    }
//}
