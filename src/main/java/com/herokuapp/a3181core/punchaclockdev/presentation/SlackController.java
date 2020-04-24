package com.herokuapp.a3181core.punchaclockdev.presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    /**
     * 休憩開始コメントを返すAPI
     *
     * @return 固定値
     */
    @RequestMapping(path = "/slack/break", method = RequestMethod.POST)
    public String breaked() {
        return "休憩開始！リラックスしましょう\uD83D\uDE0A";
    }

    /**
     * 休憩終了コメントを返すAPI
     *
     * @return 固定値
     */
    @RequestMapping(path = "/slack/return", method = RequestMethod.POST)
    public String returned() {
        return "休憩終了！適度に頑張りましょう\uD83C\uDFC3\u200D♂️";
    }
}
