package com.herokuapp.a3181core.punchaclockdev.presentation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String hello() {
        final LocalDateTime now = LocalDateTime.now();
        return "Hello World, Now " + now.format(timeFormat);
    }

}
