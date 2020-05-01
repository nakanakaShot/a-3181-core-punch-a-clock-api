package com.herokuapp.a3181core.punchaclockdev.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.message.Message;

public class SlackDTO {

    //SlackAPIからのJsonのレスポンスを受け取る処理
    @JsonProperty("slackAPI")

    private Boolean ok;
    private String channel;
    private String ts;

    private Message message;
    private String text;
    private String username;
    private String bot_id;
    private String attachments;
    private String id;
    private String fallback;

    private String type;
    private String bot_message;


}
