package com.herokuapp.a3181core.punchaclockdev;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class AttendControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void nameTest() throws Exception {
        this.mockMvc.perform(get("/?name=hoge"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("hoge")));

    }

    @Test
    public void headerTest() throws Exception {
        this.mockMvc.perform(get("/header")
            .header(HttpHeaders.USER_AGENT, HttpHeaders.USER_AGENT))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("Request-Header=User-Agent")));

    }
}
