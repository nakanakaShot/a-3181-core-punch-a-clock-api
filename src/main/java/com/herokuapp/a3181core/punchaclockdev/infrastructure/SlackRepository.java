package com.herokuapp.a3181core.punchaclockdev.infrastructure;

import com.herokuapp.a3181core.punchaclockdev.configure.AppProperties;
import com.herokuapp.a3181core.punchaclockdev.domain.model.SlackParam;
import com.herokuapp.a3181core.punchaclockdev.exception.SlackApiPostUnexpectedException;
import com.herokuapp.a3181core.punchaclockdev.shared.ClockProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ResponseBody
@Repository
@RequiredArgsConstructor
public class SlackRepository {

    private final RestTemplate restTemplate;
    private final AppProperties appProperties;
    private final ClockProvider clockProvider;

    /**
     * requestParamにデータを詰め込んでSlackAPIにpost
     *
     * @param slackParam : tokenやteamIdなど。詳細はslackParam.javaを参照
     */
    public ResponseEntity<SlackDto> postParam(SlackParam slackParam) {
        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
        requestParam.add("token", appProperties.getSlack().getAppToken());
        requestParam.add("channel", appProperties.getSlack().getChannelPostedToId());
        requestParam
            .add("text",
                "現在時刻 " + clockProvider.nowAsFormatted() + " 名前 " + slackParam.getUserName());
        try {
            return restTemplate
                .postForEntity("https://slack.com/api/chat.postMessage", requestParam,
                    SlackDto.class);
        } catch (RestClientException ex) {
            throw new SlackApiPostUnexpectedException(ex);
        }

    }
}
