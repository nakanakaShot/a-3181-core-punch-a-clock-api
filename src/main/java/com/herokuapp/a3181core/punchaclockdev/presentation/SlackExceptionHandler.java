package com.herokuapp.a3181core.punchaclockdev.presentation;

import com.herokuapp.a3181core.punchaclockdev.exception.SlackApiPostUnexpectedException;
import com.herokuapp.a3181core.punchaclockdev.exception.SlackAuthenticatorUnexpectedException;
import com.herokuapp.a3181core.punchaclockdev.exception.SlackUnsignedRequestException;
import com.herokuapp.a3181core.punchaclockdev.shared.ErrorMessageDef;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> handleException(SlackUnsignedRequestException ex) {
        return new ResponseEntity<>(
            ErrorMessageDef.FAILED_TO_POST.getMessage(),
            ErrorMessageDef.FAILED_TO_POST.getStatus());
    }

    /**
     * Slack認証予期せぬ例外
     *
     * @param ex 例外
     * @return エラーレスポンス
     */

    @ExceptionHandler({SlackAuthenticatorUnexpectedException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(SlackAuthenticatorUnexpectedException ex) {
        return new ResponseEntity<>(
            ErrorMessageDef.FAILED_TO_ACCESS.getMessage(),
            ErrorMessageDef.FAILED_TO_ACCESS.getStatus());
    }

    /**
     * SlackAPIへのPOST時の予期せぬ例外
     *
     * @param ex 例外
     * @return エラーレスポンス
     */
    @ExceptionHandler({SlackApiPostUnexpectedException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(SlackApiPostUnexpectedException ex) {
        return new ResponseEntity<>(
            ErrorMessageDef.FAILED_TO_SLACK_API_POST.getMessage(),
            ErrorMessageDef.FAILED_TO_SLACK_API_POST.getStatus());
    }
}