package com.herokuapp.a3181core.punchaclockdev.infrastructure;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.herokuapp.a3181core.punchaclockdev.configure.AppProperties;
import com.herokuapp.a3181core.punchaclockdev.configure.AppProperties.Slack;
import com.herokuapp.a3181core.punchaclockdev.domain.model.SlackParam;
import com.herokuapp.a3181core.punchaclockdev.shared.ClockProvider;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class SlackRepositoryTest {

    @Autowired
    private SlackRepository slackRepository;

    @Autowired
    private RestTemplate restTemplate;

    @MockBean
    private AppProperties appProperties;
    @MockBean
    private ClockProvider clockProvider;

    //taken,channelに値が入っているかのテスト
    //@Test
    void postParamTest() {

        SlackParam param = new SlackParam();
        param.setUserName("Tarou");

        //テスト中に返す値 これがないと全てnullになる
        Slack slack = new Slack("aaa", "AAA", "BBB");
        when(appProperties.getSlack()).thenReturn(slack);
        when(clockProvider.nowAsFormatted()).thenReturn("CCC");

        //期待値
        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
        requestParam.add("token", "AAA");
        requestParam.add("channel", "BBB");
        requestParam.add("text", "現在時刻 CCC 名前 Tarou");

        slackRepository.postParam(param);

        verify(restTemplate, times(1))
            .postForObject("https://slack.com/api/chat.postMessage",
                requestParam, HttpServletResponse.class);
    }

    // Json返り値を想定通り格納できているかのテスト
    @Test
    void testNormal() {

        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();

        String returnJson = "{\n"
            + "    \"ok\": true,\n"
            + "    \"channel\": \"C1H9RESGL\",\n"
            + "    \"ts\": \"1503435956.000247\",\n"
            + "    \"message\": {\n"
            + "        \"text\": \"Here's a message for you\",\n"
            + "        \"username\": \"ecto1\",\n"
            + "        \"bot_id\": \"B19LU7CSY\",\n"
            + "        \"attachments\": [\n"
            + "            {\n"
            + "                \"text\": \"This is an attachment\",\n"
            + "                \"id\": 1,\n"
            + "                \"fallback\": \"This is an attachment's fallback\"\n"
            + "            }\n"
            + "        ],\n"
            + "        \"type\": \"message\",\n"
            + "        \"subtype\": \"bot_message\",\n"
            + "        \"ts\": \"1503435956.000247\"\n"
            + "    }\n"
            + "}";

        mockServer.expect(requestTo("https://slack.com/api/chat.postMessage"))
            .andRespond(withSuccess(returnJson, MediaType.APPLICATION_JSON));

        SlackParam param = new SlackParam();
        param.setUserName("Tarou");

        ResponseEntity<SlackDto> slackDto = slackRepository.postParam(param);

        System.out.println(slackDto);
        mockServer.verify();

    }
}
