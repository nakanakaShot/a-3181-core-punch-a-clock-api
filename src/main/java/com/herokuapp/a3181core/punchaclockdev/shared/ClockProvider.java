package com.herokuapp.a3181core.punchaclockdev.shared;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 現在時刻を返すクラス
 */
@Component
@RequiredArgsConstructor
public class ClockProvider {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter
        .ofPattern("uuuu/MM/dd HH:mm:ss");

    private final Clock clock;

    /**
     * 現在時刻をString型にフォーマット
     */
    public String now() {
        return LocalDateTime.now(clock).format(TIME_FORMAT);
    }

    ZoneId getZone() {
        return clock.getZone();
    }
}
