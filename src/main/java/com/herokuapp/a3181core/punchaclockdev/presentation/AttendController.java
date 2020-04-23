package com.herokuapp.a3181core.punchaclockdev.presentation;

import com.herokuapp.a3181core.punchaclockdev.domain.service.AttendService;
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

    private static final DateTimeFormatter timeFormat = DateTimeFormatter
        .ofPattern("uuuu/MM/dd HH:mm:ss");

    /**
     * リクエストパラメータと固定値を連結するAPI
     *
     * @param name : 練習用リクエストパラメータ
     * @return "/"に対するレスポンス文字列
     */
    @RequestMapping(path = "/", method = {RequestMethod.GET,
        RequestMethod.POST})
    public String attend(@RequestParam("name") String name) {
        LocalDateTime now = LocalDateTime.now();

        return "Attend, starttime=" + now.format(timeFormat) + ", name=" + name + ", repository="
            + attendService.parameterBridge(name);
    }

    /**
     * UserAgentを表示するAPI
     *
     * @param userAgent : リクエストしたUserAgent
     * @return UserAgent
     */
    @RequestMapping(path = "/header", method = {RequestMethod.GET,
        RequestMethod.POST})
    public String getRequestHeader(@RequestHeader(HttpHeaders.USER_AGENT) String userAgent) {
        return "Request-Header=" + userAgent;
    }

}
