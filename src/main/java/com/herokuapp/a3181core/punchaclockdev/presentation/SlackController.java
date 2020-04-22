package com.herokuapp.a3181core.punchaclockdev.presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlackController {

    /**
     * 出勤コメントを返すAPI
     *
     * @return 固定値
     */
    @RequestMapping(path = "/slack/attend")
    public String attend() {
        return "出勤！おはようございます\uD83C\uDF1E";
    }
}
