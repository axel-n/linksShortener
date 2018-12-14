package com.example.links_shortener.dao;

import com.example.links_shortener.model.Link;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class WebControllerTest extends DatabaseTest {

    private static final String TEST_URL1 = "test1.com";

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void openLink() {

        Link link1 = new Link();

        // receive user from browser/api (before save in bd)
        String resultShortUrl = link1.setLongUrl(TEST_URL1);
        linkRepository.save(link1);

        int clickBeforeOpen  = link1.getClicks();
        assertEquals(0, clickBeforeOpen);

        // goto to longUrl by shortUrl
        try {
            this.mockMvc.perform(get("/" + resultShortUrl)).andDo(print()).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // and backend increment statistic clicks
        int clickAfterOpen = linkRepository.findByShortUrl(resultShortUrl).getClicks();
        assertEquals(1, clickAfterOpen);
    }

    @Test
    public void notFound() {
        // TODO
        // this
    }
}
