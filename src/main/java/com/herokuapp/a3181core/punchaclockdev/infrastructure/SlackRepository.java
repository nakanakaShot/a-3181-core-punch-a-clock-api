package com.herokuapp.a3181core.punchaclockdev.infrastructure;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Repository
public class SlackRepository {

    private final RestTemplate restTemplate;

    // RestTemplateをコンストラクタインジェクションする
    public SlackRepository(RestTemplateBuilder restTemplateBuilder) {
        // RestTemplateインスタンスを生成する
        this.restTemplate = restTemplateBuilder.build();
    }

    public String hoge() {
        MultiValueMap<String, String> requestparam = new LinkedMultiValueMap<>();
        requestparam.add("token", "TOKEN");
        requestparam.add("channel", "CHANNEL");
        requestparam.add("text", "今日は雷");
        String getObject = restTemplate
            .postForObject("https://slack.com/api/chat.postMessage", requestparam, String.class);
        System.out.println("here!!!!!!!");
        System.out.println(getObject);
        return "";
    }
}
