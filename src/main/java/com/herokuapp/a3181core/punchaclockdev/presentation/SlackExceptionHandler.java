package com.herokuapp.a3181core.punchaclockdev.presentation;

import com.herokuapp.a3181core.punchaclockdev.exception.SlackUnsignedRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SlackExceptionHandler {

    /**
     * Slack未認証リクエスト例外
     *
     * @param ex 例外
     * @return エラーレスポンス
     */

    @ExceptionHandler({SlackUnsignedRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(SlackUnsignedRequestException ex) {
        return "失敗！";
    }
}
