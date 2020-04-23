package com.herokuapp.a3181core.punchaclockdev.presentation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class SlackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void attendTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/slack/attend"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content()
                .string("出勤！おはようございます\uD83C\uDF1E"));
    }

    @Test
    void returnPostTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/slack/return"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content()
                .string("休憩終了！適度に頑張りましょう\uD83C\uDFC3\u200D♂️"));
    }

    @Test
    void returnGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/slack/return"))
            .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }
}
