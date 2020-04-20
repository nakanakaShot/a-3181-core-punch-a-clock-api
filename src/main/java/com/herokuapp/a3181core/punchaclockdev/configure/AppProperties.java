package com.herokuapp.a3181core.punchaclockdev.configure;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app")
@ConstructorBinding
@AllArgsConstructor
@Getter
@Validated
public class AppProperties {

    @NotNull
    private final Slack slack;

    @AllArgsConstructor
    @Getter
    public static class Slack {

        @NotBlank
        private final String appToken;
        @NotBlank
        private final String channelPostedToId;
    }
}
