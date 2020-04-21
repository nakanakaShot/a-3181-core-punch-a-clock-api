package com.herokuapp.a3181core.punchaclockdev;

import com.herokuapp.a3181core.punchaclockdev.shared.ClockProvider;
import org.springframework.beans.factory.annotation.Autowired;

public class ClockProviderTest {

    @Autowired
    ClockProvider clockProvider;

    public void timeTest() {

        //Clock test = Clock.fixed(Instant.parse("2018-04-29T10:15:30.00Z"), ZoneId.of("Asia/Tokyo"));
    }

//    private static Clock clock = Clock.systemDefaultZone();
//    private static ZoneId zoneId = ZoneId.systemDefault();
//
//    public static LocalDateTime now() {
//        return LocalDateTime.now(getClock());
//    }
}
