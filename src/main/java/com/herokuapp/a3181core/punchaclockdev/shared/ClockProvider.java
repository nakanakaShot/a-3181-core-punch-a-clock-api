package com.herokuapp.a3181core.punchaclockdev.shared;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

/**
 * 現在時刻を返すクラス
 */
@Component
public class ClockProvider {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter
        .ofPattern("uuuu/MM/dd HH:mm:ss");

    /**
     * 現在時刻をString型にフォーマット
     */
    public String now() {
        return LocalDateTime.now().format(TIME_FORMAT);
    }

}
