package com.herokuapp.a3181core.punchaclockdev.presentation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AttendController {

    private static final DateTimeFormatter timeFormat = DateTimeFormatter
        .ofPattern("uuuu/MM/dd HH:mm:ss");

    @RequestMapping(path = "/", method = {RequestMethod.GET,
        RequestMethod.POST})
    public String attend(@RequestParam("name") String name) {
        LocalDateTime now = LocalDateTime.now();
        return "Attend, starttime=" + now.format(timeFormat) + ", name=" + name;
    }

    @RequestMapping(path = "/header", method = {RequestMethod.GET,
        RequestMethod.POST})
    public String getRequestHeader(@RequestHeader(HttpHeaders.USER_AGENT) String userAgent) {
        return "Request-Header=" + userAgent;
    }

}
