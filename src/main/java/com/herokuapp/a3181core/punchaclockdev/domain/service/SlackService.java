package com.herokuapp.a3181core.punchaclockdev.domain.service;

import com.herokuapp.a3181core.punchaclockdev.domain.model.SlackParam;
import com.herokuapp.a3181core.punchaclockdev.infrastructure.SlackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlackService {

    private final SlackRepository slackRepository;

    /**
     * slackParamを受け取り、repositoryでSlackAPIにデータを送るpostParamを実行
     *
     * @param slackParam : tokenやteamIdなど。詳細はslackParam.javaを参照
     */
    public void postParamBridge(SlackParam slackParam) {
        slackRepository.postParam(slackParam);
    }
}
