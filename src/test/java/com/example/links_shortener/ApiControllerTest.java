package com.example.links_shortener;

import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class ApiControllerTest {

    private static final String TEST_URL1 = "https://ya.ru";
    private static final int COUNT_CHARS_FOR_SHORT_URL = 5;
    private static final int GUEST_USER_ID = 0;
    private static final int CLICKS_INITIALIZE = 0;

    private Map<String, String> jsonLink;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void initializeGuestLink() {
        try { MvcResult result = this.mockMvc.perform(post("/api/link")
                    .param("longUrl", TEST_URL1))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();

            String json = result.getResponse().getContentAsString();
            this.jsonLink = JsonPath.read(json, "$");

            assertEquals(COUNT_CHARS_FOR_SHORT_URL,  jsonLink.get("shortUrl").length());
            assertEquals(TEST_URL1,  jsonLink.get("longUrl"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDataForGuest() {

        assertNull(jsonLink.get("clicks"));
        assertNull(jsonLink.get("created"));
        assertNull(jsonLink.get("userId"));

    }
}
