package com.herokuapp.a3181core.punchaclockdev.shared;

import java.time.Clock;
import java.time.ZoneId;
import org.springframework.stereotype.Component;

@Component
public class ClockProvider {

    public Clock now() {
        return Clock.system(ZoneId.of("Asia/Tokyo"));
    }

}
