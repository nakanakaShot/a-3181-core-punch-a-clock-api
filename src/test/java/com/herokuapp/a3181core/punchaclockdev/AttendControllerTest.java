package com.herokuapp.a3181core.punchaclockdev;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @ParameterizedTest
    @CsvSource({
        "/?name=hoge, hoge", //
        "/?name=fuga, fuga", //
        "/?name=テスト, テスト", //
        "/?name=123, 123", //
        "'/?name= ',' '", //
        "/?name=,''", //
        "/?name=%E3%81%82, %E3%81%82", //
    })
    void nameNormalTest(String query, String name) throws Exception {
        //serviceからGoodbye_Worldをcontrollerに入力した想定のテストをしたい(設定しないとnull)
        //mockが機能しているのか、実装の方が動いているのか判断するためにGoodbye_Worldを返させたい
        when(attendService.parameterBridge(anyString())).thenReturn("Goodbye_World");

        //mockmvcからcontrollerにqueryを投げさせる
        this.mockMvc.perform(get(query))

            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(name)))
            //Goodbye_Worldがcontrollerから出力されることを確認したい
            .andExpect(content().string(containsString("Goodbye_World")));

        //controllerからserviceへの出力した際に、クエリの中身を引数として渡し、
        //parameterBridgeを1度通過するか検証
        verify(attendService, times(1)).parameterBridge(name);
    }

    //異常系テスト
    @Test
    void nameErrorTest() throws Exception {
        when(attendService.parameterBridge(anyString())).thenReturn("Goodbye_World");
        this.mockMvc.perform(get("/"))
            .andDo(print()).andExpect(status().isBadRequest());

        verify(attendService, never()).parameterBridge(any());
    }

    @Test
    void headerTest() throws Exception {
        this.mockMvc.perform(get("/header")
            .header(HttpHeaders.USER_AGENT, HttpHeaders.USER_AGENT))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("Request-Header=User-Agent")));

    }

}
