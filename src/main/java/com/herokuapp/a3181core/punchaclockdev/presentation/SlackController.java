package com.herokuapp.a3181core.punchaclockdev.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlackController {

    /**
     * 出勤コメントを返すAPI
     *
     * @return 固定値
     */
    @PostMapping(path = "/slack/attend")
    public String attend() {
        return "出勤！おはようございます\uD83C\uDF1E";
    }

    /**
     * 退勤コメントを返すAPI
     *
     * @return 固定値
     */
    @PostMapping(path = "/slack/dismiss")
    public String dismiss() {
        return "退勤！お疲れさまでした \uD83D\uDECF";
    }
}
