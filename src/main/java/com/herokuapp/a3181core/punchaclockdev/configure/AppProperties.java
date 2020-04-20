package com.herokuapp.a3181core.punchaclockdev.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "app")
@Data
@ConstructorBinding
public class AppProperties {

    private Slack slack;

    @Data
    public static class Slack {

        private String appToken;
        private String channelPostedToId;
    }
}
