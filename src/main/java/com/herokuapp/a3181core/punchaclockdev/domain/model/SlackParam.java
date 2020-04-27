package com.herokuapp.a3181core.punchaclockdev.domain.model;

import lombok.Data;

@Data
public class SlackParam {

    private String token;
    private String teamId;
    private String teamDomain;
    private String enterpriseId;
    private String enterpriseName;
    private String channelId;
    private String channelName;
    private String userId;
    private String userName;
    private String command;
    private String text;
    private String responseUrl;
    private String triggerId;
}
