package com.herokuapp.a3181core.punchaclockdev.presentation;

import com.herokuapp.a3181core.punchaclockdev.domain.model.SlackParam;
import com.herokuapp.a3181core.punchaclockdev.domain.service.SlackService;
import com.herokuapp.a3181core.punchaclockdev.exception.SlackUnsignedRequestException;
import com.herokuapp.a3181core.punchaclockdev.shared.SlackAuthenticator;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SlackController {

    private final SlackService slackService;

    private final SlackAuthenticator slackAuthenticator;

    /**
     * 出勤コメントを返すAPI
     *
     * @param slackParam Slackコマンドから送られるリクエストパラメータ
     * @param result     bindの結果格納
     * @param request    リクエスト
     * @return 固定値
     */
    @PostMapping(path = "/slack/attend")
    public String attend(@Validated SlackParam slackParam, BindingResult result,
        HttpServletRequest request) {
        validateIfSignedRequestFromSlack(request);
        slackService.postParamBridge(slackParam);
        return "出勤！おはようございます\uD83C\uDF1E";
    }

    /**
     * 休憩開始コメントを返すAPI
     *
     * @param request リクエスト
     * @return 固定値
     */
    @RequestMapping(path = "/slack/break", method = RequestMethod.POST)
    public String breaked(HttpServletRequest request) {
        validateIfSignedRequestFromSlack(request);
        return "休憩開始！リラックスしましょう\uD83D\uDE0A";
    }

    /**
     * 休憩終了コメントを返すAPI
     *
     * @param slackParam Slackコマンドから送られるリクエストパラメータ
     * @param result     bindの結果格納
     * @param request    リクエスト
     * @return 固定値
     */
    @RequestMapping(path = "/slack/return", method = RequestMethod.POST)
    public String returned(@Validated SlackParam slackParam, BindingResult result,
        HttpServletRequest request) {
        validateIfSignedRequestFromSlack(request);
        return "休憩終了！適度に頑張りましょう\uD83C\uDFC3\u200D♂️";
    }

    /**
     * 退勤コメントを返すAPI
     *
     * @param slackParam Slackコマンドから送られるリクエストパラメータ
     * @param result     bindの結果格納
     * @param request    リクエスト
     * @return 固定値
     */
    @PostMapping(path = "/slack/dismiss")
    public String dismiss(@Validated SlackParam slackParam, BindingResult result,
        HttpServletRequest request) {
        validateIfSignedRequestFromSlack(request);
        return "退勤！お疲れさまでした \uD83D\uDECF";
    }

    private void validateIfSignedRequestFromSlack(HttpServletRequest request) {
        if (slackAuthenticator.isSignedRequestFromSlack(request)) {
            return;
        }
        throw new SlackUnsignedRequestException();
    }

    /**
     * listを返すAPI
     *
     * @return 固定値
     */
    @PostMapping(path = "/slack/list")
    public String list() {
        return "hello, world";
    }

    /**
     * リクエストパラメータのbind設定
     *
     * @param binder  リクエストに対するWebDataBinder
     * @param request リクエスト時のServletオブジェクト
     */
    @InitBinder("slackParam")
    public void initSlackParamBinder(WebDataBinder binder, HttpServletRequest request) {
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("token", request.getParameter("token"));
        propertyValues.add("teamId", request.getParameter("team_id"));
        propertyValues.add("teamDomain", request.getParameter("team_domain"));
        propertyValues.add("enterpriseId", request.getParameter("enterprise_id"));
        propertyValues.add("enterpriseName", request.getParameter("enterprise_name"));
        propertyValues.add("channelId", request.getParameter("channel_id"));
        propertyValues.add("channelName", request.getParameter("channel_name"));
        propertyValues.add("userId", request.getParameter("user_id"));
        propertyValues.add("userName", request.getParameter("user_name"));
        propertyValues.add("command", request.getParameter("command"));
        propertyValues.add("text", request.getParameter("text"));
        propertyValues.add("responseUrl", request.getParameter("response_url"));
        propertyValues.add("triggerId", request.getParameter("trigger_id"));
        binder.bind(propertyValues);

    }
}
