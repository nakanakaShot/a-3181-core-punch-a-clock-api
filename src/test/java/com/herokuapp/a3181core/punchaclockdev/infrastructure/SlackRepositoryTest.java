package com.herokuapp.a3181core.punchaclockdev.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.herokuapp.a3181core.punchaclockdev.domain.model.SlackParam;
import com.herokuapp.a3181core.punchaclockdev.exception.SlackApiPostUnexpectedException;
import com.herokuapp.a3181core.punchaclockdev.infrastructure.SlackDto.Message;
import com.herokuapp.a3181core.punchaclockdev.infrastructure.SlackDto.Message.Attachment;
import com.herokuapp.a3181core.punchaclockdev.shared.ClockProvider;
import java.util.Collections;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SlackRepositoryTest {

    @Autowired
    private SlackRepository slackRepository;

    @Autowired
    private RestTemplate restTemplate;

    //    @MockBean
//    private AppProperties appProperties;
    @MockBean
    private ClockProvider clockProvider;

    //taken,channelに値が入っているかのテスト
    //@Test
    void postParamTest() {

        SlackParam param = new SlackParam();
        param.setUserName("Tarou");

        //テスト中に返す値 これがないと全てnullになる
//        Slack slack = new Slack("aaa", "AAA", "BBB");
//        when(appProperties.getSlack()).thenReturn(slack);
//        when(clockProvider.nowAsFormatted()).thenReturn("CCC");

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

        // Service -> Repositoryの入力値
        SlackParam param = new SlackParam();
        param.setUserName("Tarou");

        // repository -> mockserver の期待値＆
        // mockserver -> repository の入力値
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

        //repository -> serviceの期待値
        Attachment attachment = new Attachment();
        attachment.setText("This is an attachment");
        attachment.setId(1);
        attachment.setFallback("This is an attachment's fallback");

        Message message = new Message();
        message.setText("Here's a message for you");
        message.setUsername("ecto1");
        message.setBotId("B19LU7CSY");
        message.setAttachments(Collections.singletonList(attachment));
        message.setType("message");
        message.setSubtype("bot_message");
        message.setTs("1503435956.000247");

        SlackDto expected = new SlackDto();
        expected.setOk(true);
        expected.setChannel("C1H9RESGL");
        expected.setTs("1503435956.000247");
        expected.setMessage(message);

        SlackDto actual = slackRepository.postParam(param).getBody();

        assertEquals(expected, actual);

        mockServer.verify();
    }


    @Test
    void test4xx() {
        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();

        mockServer.expect(requestTo("https://slack.com/api/chat.postMessage"))
            .andRespond(withStatus(HttpStatus.UNAUTHORIZED));

        SlackParam param = new SlackParam();
        param.setUserName("Tarou");

        assertThrows(SlackApiPostUnexpectedException.class,
            () -> slackRepository.postParam(param));
    }
}
