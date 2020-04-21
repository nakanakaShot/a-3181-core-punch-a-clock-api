package com.herokuapp.a3181core.punchaclockdev.presentation;

import com.herokuapp.a3181core.punchaclockdev.domain.service.AttendService;
import com.herokuapp.a3181core.punchaclockdev.shared.ClockProvider;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AttendController {

    private final AttendService attendService;
    private final ClockProvider clockProvider;

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter
        .ofPattern("uuuu/MM/dd HH:mm:ss");

    @RequestMapping(path = "/", method = {RequestMethod.GET,
        RequestMethod.POST})
    public String attend(@RequestParam("name") String name) {

        return "Attend, starttime="
            + LocalDateTime.now(clockProvider.now()).format(TIME_FORMAT)
            + ", name=" + name
            + ", repository="
            + attendService.parameterBridge(name);
    }

    @RequestMapping(path = "/header", method = {RequestMethod.GET,
        RequestMethod.POST})
    public String getRequestHeader(@RequestHeader(HttpHeaders.USER_AGENT) String userAgent) {
        return "Request-Header=" + userAgent;
    }

}
