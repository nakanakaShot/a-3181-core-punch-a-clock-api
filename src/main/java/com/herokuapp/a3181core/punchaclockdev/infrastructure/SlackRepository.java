package com.herokuapp.a3181core.punchaclockdev.infrastructure;

import com.herokuapp.a3181core.punchaclockdev.configure.AppProperties;
import com.herokuapp.a3181core.punchaclockdev.domain.model.SlackParam;
import com.herokuapp.a3181core.punchaclockdev.shared.ClockProvider;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Repository
public class SlackRepository {

    private final RestTemplate restTemplate;
    private final AppProperties appProperties;
    private final ClockProvider clockProvider;

    /**
     * restTemplateのみrestTemplateBuilder.build()を代入しているので、自動生成されるコンストラクタでは対応できない為、記述している。
     * restTemplate, appProperties, clockProviderのコンストラクタインジェクション
     *
     * @param restTemplateBuilder :restTemplate(データ送信)を使う為に必要
     * @param appProperties       :IntelliJ上に保存した環境変数
     * @param clockProvider       :同一の現在時刻
     */
    public SlackRepository(RestTemplateBuilder restTemplateBuilder, AppProperties appProperties,
        ClockProvider clockProvider) {
        // RestTemplateインスタンスを生成する
        this.restTemplate = restTemplateBuilder.build();
        this.appProperties = appProperties;
        this.clockProvider = clockProvider;
    }

    /**
     * requestParamにデータを詰め込んでSlackAPIにpost
     *
     * @param slackParam : tokenやteamIdなど。詳細はslackParam.javaを参照
     */
    public void postParam(SlackParam slackParam) {
        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
        requestParam.add("token", appProperties.getSlack().getAppToken());
        requestParam.add("channel", appProperties.getSlack().getChannelPostedToId());
        requestParam
            .add("text",
                "現在時刻 " + clockProvider.nowAsFormatted() + " 名前 " + slackParam.getUserName());
        restTemplate
            .postForObject("https://slack.com/api/chat.postMessage", requestParam, String.class);

    }
}
