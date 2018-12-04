package com.example.linksShortener.repository;

import com.example.linksShortener.model.Link;
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
public class HtmlController extends DatabaseTest {

    @Autowired
    private ILinkRepository linkService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void openLink() throws Exception {

        Link link1 = new Link("https://ya.ru");
        String shortUrl = linkService.saveLink(link1);

        System.out.println(shortUrl);

        int clickBeforeOpen = linkService.findByShortUrl(shortUrl).getClicks();
        assertEquals(0, clickBeforeOpen);

        // goto to longUrl by shortUrl
        this.mockMvc.perform(get("/" + shortUrl)).andDo(print()).andExpect(status().isOk());

        // and backend increment statistic clicks
        int clickAfterOpen = linkService.findByShortUrl(shortUrl).getClicks();
        assertEquals(1, clickAfterOpen);
    }
}
