package com.herokuapp.a3181core.punchaclockdev;

import java.time.Clock;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * SpringBootが Clock型インスタンス をDIできるようにするためのconfigクラス
 */
@Configuration
@ComponentScan("com.herokuapp.a3181core.punchaclockdev")
@RequiredArgsConstructor
public class ClockConfig {

    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Tokyo");

    /**
     * コンポーネントにDIするとき(Autowired, final + RequiredArgsConstructor) これが呼ばれる
     *
     * @return 東京タイムゾーンの現在時刻
     */
    @Bean
    public Clock clock() {
        return Clock.system(ZONE_ID);
    }
}

