package com.herokuapp.a3181core.punchaclockdev;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.herokuapp.a3181core.punchaclockdev.domain.service.AttendService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("ServiceMockTest")
public class AttendControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AttendService attendService;

    @Test
    void nameTest() throws Exception {
        when(attendService.parameterBridge(anyString())).thenReturn("Goodbye_World");
        this.mockMvc.perform(get("/?name=hoge"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("hoge")))
            .andExpect(content().string(containsString("Goodbye_World")));

        verify(attendService, times(1)).parameterBridge("hoge");
    }

    @Test
    public void headerTest() throws Exception {
        this.mockMvc.perform(get("/header")
            .header(HttpHeaders.USER_AGENT, HttpHeaders.USER_AGENT))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("Request-Header=User-Agent")));

    }

}
