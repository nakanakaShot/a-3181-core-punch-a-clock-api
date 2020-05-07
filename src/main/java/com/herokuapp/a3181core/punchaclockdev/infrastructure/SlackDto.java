package com.herokuapp.a3181core.punchaclockdev.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SlackDto {

    //SlackAPIからのJsonのレスポンスを受け取る処理
    @JsonProperty("slackAPI")

    private Boolean ok;
    private String channel;
    private String ts;

    @Override
    public String toString() {
        return "ok: " + ok +
            ", channel: " + channel +
            ", ts: " + ts;
    }

    private Message message;

    public static class Message {

        private String text;
        private String username;
        private String bot_id;
        private List<Attachment> attachments;

        private String type;
        private String subtype;
        private String ts;

        @Override
        public String toString() {
            return getClass().getSimpleName() +
                ": text: " + text +
                ": username: " + username +
                ": bot_id: " + bot_id +
                ": attachments: " + attachments +
                ": type: " + type +
                ": subtype: " + subtype +
                ": ts: " + ts;
        }
    }

    public static class Attachment {

        private String text;
        private Integer id;
        private String fallback;

        @Override
        public String toString() {
            return getClass().getSimpleName() +
                ": text: " + text +
                ", id: " + id +
                ", fallback: " + fallback;
        }
    }

//    ObjectMapper mapper = new ObjectMapper();
//    SlackDTO dto = mapper.readValue(json, SlackDTO.class);

}
