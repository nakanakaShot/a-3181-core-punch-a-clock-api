package com.herokuapp.a3181core.punchaclockdev.infrastructure;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.herokuapp.a3181core.punchaclockdev.configure.AppProperties;
import com.herokuapp.a3181core.punchaclockdev.configure.AppProperties.Slack;
import com.herokuapp.a3181core.punchaclockdev.domain.model.SlackParam;
import com.herokuapp.a3181core.punchaclockdev.shared.ClockProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class SlackRepositoryTest {

    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private AppProperties appProperties;
    @MockBean
    private ClockProvider clockProvider;

    @Autowired
    private SlackRepository slackRepository;

    //taken,channelに値が入っているかのテスト
    @Test
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
                requestParam, String.class);
    }

}
