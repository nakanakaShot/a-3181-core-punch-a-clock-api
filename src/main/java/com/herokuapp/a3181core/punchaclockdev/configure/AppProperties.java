package com.herokuapp.a3181core.punchaclockdev.configure;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "app")
@Value
@ConstructorBinding
public class AppProperties {

    private final Slack slack;

    @Value
    public static class Slack {

        private final String appToken;
        private final String channelPostedToId;
    }
}
