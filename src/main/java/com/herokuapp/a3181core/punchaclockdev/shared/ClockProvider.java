package com.herokuapp.a3181core.punchaclockdev.shared;

import java.time.Clock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClockProvider {

    private final Clock clock;

    public Clock now() {
        return clock;
    }
}
