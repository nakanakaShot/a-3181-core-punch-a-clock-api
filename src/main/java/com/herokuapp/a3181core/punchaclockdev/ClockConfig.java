package com.herokuapp.a3181core.punchaclockdev;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// SpringBootが Clock型インスタンス をDIできるようにするためのconfigクラス
@Configuration
@RequiredArgsConstructor
public class ClockConfig {

    private static final ZoneId zoneId = ZoneId.of("Asia/Tokyo");

    // コンポーネントにDIするとき(Autowired, final + RequiredArgsConstructor) これが呼ばれる
    @Bean
    public Clock clock() {
        // TODO bootrunされたときに分かるように明示的に時間を固定しているので、devへマージするときは Clock.now(zoneId) に修正する事
        return Clock.fixed(Instant.parse("2018-04-29T10:15:30.00Z"), zoneId);
    }
}
