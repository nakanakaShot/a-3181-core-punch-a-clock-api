package com.herokuapp.a3181core.punchaclockdev.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.herokuapp.a3181core.punchaclockdev.ClockConfig;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * ClockProviderのテスト
 */
@SpringBootTest
@ContextConfiguration(classes = ClockConfig.class)
class ClockProviderTest {

    @Autowired
    private ClockProvider clockProvider;

    private final ClockProvider fixedClockProvider = new ClockProvider(
        Clock.fixed(Instant.parse("2018-04-29T10:15:30.00Z"), ZoneId.of("Asia/Tokyo")));

    /**
     * JSTでClockがインスタンス化されているかのテスト
     */
    @Test
    void timeZoneTest() {
        assertEquals(ZoneId.of("Asia/Tokyo"), clockProvider.getZone());
    }

    /**
     * clockProviderのnow()が意図した時刻を返却するかのテスト
     */
    @Test
    void nowTest() {
        long expect = Instant.parse("2018-04-29T10:15:30.00Z").getEpochSecond();
        assertEquals(expect, fixedClockProvider.now());

    }

    /**
     * clockProviderの getFormatted()が 意図したフォーマットをされているかのテスト
     */
    @Test
    void timeFormatTest() {
        assertEquals("2018/04/29 19:15:30", fixedClockProvider.getFormatted());

    }
}
