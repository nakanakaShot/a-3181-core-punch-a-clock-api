package com.herokuapp.a3181core.punchaclockdev.presentation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private static final DateTimeFormatter timeFormat = DateTimeFormatter
        .ofPattern("uuuu/MM/dd HH:mm:ss");

    @RequestMapping(path = "/", method = {RequestMethod.GET,
        RequestMethod.POST}, headers = "Accept=application/*")
    public String hello(@RequestParam("name") String name) {
        LocalDateTime now = LocalDateTime.now();
        return "HelloWorld, Now " + now.format(timeFormat) + " YourName " + name;
    }

}
