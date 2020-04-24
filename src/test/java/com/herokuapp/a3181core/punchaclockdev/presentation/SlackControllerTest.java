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
    void attendPostTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/slack/attend"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content()
                .string("出勤！おはようございます\uD83C\uDF1E"));
    }
  
    @Test
    void attendGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/slack/attend"))
            .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    void breakPostTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/slack/break"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content()
                .string("休憩開始！リラックスしましょう\uD83D\uDE0A"));
    }

    @Test
    void breakGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/slack/return"))
            .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
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

    @Test
    void dismissPostTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/slack/dismiss"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content()
                .string("退勤！お疲れさまでした \uD83D\uDECF"));
    }

    @Test
    void dismissGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/slack/dismiss"))
            .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }
}
