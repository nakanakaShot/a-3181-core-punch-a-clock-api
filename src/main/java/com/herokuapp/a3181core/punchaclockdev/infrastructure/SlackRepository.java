package com.herokuapp.a3181core.punchaclockdev.infrastructure;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
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
        String getObject = restTemplate.postForObject("http://inet-ip.info/", null, String.class);
        System.out.println("here!!!!!!!");
        System.out.println(getObject);
        return "";
    }
}
