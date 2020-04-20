package com.herokuapp.a3181core.punchaclockdev.configure;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app")
@ConstructorBinding
@Value
@Validated
public class AppProperties {

    @NotNull
    private Slack slack;

    @Value
    public static class Slack {

        @NotBlank
        private String appToken;
        @NotBlank
        private String channelPostedToId;
    }
}
