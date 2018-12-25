package com.example.links_shortener;

import com.example.links_shortener.core.dao.LinkRepository;
import com.example.links_shortener.core.model.Link;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class WebControllerTest extends DatabaseTest {

    private static final String TEST_URL1 = "https://ya.ru";

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void openLinkAndRedirect() {

        Link link = new Link(TEST_URL1);

        link = linkRepository.save(link);

        // receive user from browser/api (before save in bd)
        String resultShortUrl = link.getShortUrl();

        int clickBeforeOpen  = link.getClicks();
        assertEquals(0, clickBeforeOpen);

        // goto to longUrl by shortUrl
        try {
            this.mockMvc.perform(get("/" + resultShortUrl)).andDo(print()).andExpect(status().is3xxRedirection());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // and backend increment statistic clicks
        int clickAfterOpen = linkRepository.findByShortUrl(resultShortUrl).getClicks();
        assertEquals(1, clickAfterOpen);
    }

    @Test
    public void linkNotFound() {

        Link link1 = new Link(TEST_URL1);
        linkRepository.save(link1);

        // goto to longUrl by shortUrl
         final String WRONG_SHORT_URL = "abc";
        try {
            this.mockMvc.perform(get("/" + WRONG_SHORT_URL))
                    .andDo(print())
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkMoveToLongUrl() {

        Link link = new Link(TEST_URL1);
        link = linkRepository.save(link);

        System.out.println(link);

        String shortlUrl = link.getShortUrl();

        try {
            this.mockMvc.perform(get("/" + shortlUrl))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
