package com.herokuapp.a3181core.punchaclockdev.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SlackDto {

    //SlackAPIからのJsonのレスポンスを受け取る処理
    @JsonProperty("slackAPI")

    private Boolean ok;
    private String channel;
    private String ts;
    private Message message;

    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Message {

        private String text;
        private String username;
        private String botId;
        private List<Attachment> attachments;

        private String type;
        private String subtype;
        private String ts;

        @Data
        @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
        public static class Attachment {

            private String text;
            private Integer id;
            private String fallback;

        }
    }
}
