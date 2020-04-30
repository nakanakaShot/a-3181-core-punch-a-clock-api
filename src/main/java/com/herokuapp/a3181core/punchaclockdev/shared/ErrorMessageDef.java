package com.herokuapp.a3181core.punchaclockdev.shared;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorMessageDef {
    FAILED_TO_POST("投稿に失敗しました。", HttpStatus.BAD_REQUEST),
    FAILED_TO_ACCESS("サーバーにアクセスできませんでした。", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;
}
