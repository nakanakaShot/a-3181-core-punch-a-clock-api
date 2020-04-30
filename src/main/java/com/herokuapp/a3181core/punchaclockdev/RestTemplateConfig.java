package com.herokuapp.a3181core.punchaclockdev;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan("com.herokuapp.a3181core.punchaclockdev")
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final RestTemplateBuilder restTemplateBuilder;

    /**
     * コンポーネントにDIするとき(Autowired, final + RequiredArgsConstructor) これが呼ばれる
     *
     * @return httpリクエストができるrestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder.build();
    }
}
