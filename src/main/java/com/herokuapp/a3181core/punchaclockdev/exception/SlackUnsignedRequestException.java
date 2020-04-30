package com.herokuapp.a3181core.punchaclockdev.exception;

public class SlackUnsignedRequestException extends RuntimeException {

    private static final long serialVersionUID = 506827925379999721L;

    /**
     * Slack未認証リクエスト例外
     */
    public SlackUnsignedRequestException() {

    }

    /**
     * @param message エラーメッセージ
     */
    public SlackUnsignedRequestException(String message) {
        super(message);
    }

    /**
     * @param message エラーメッセージ
     * @param cause   ネスト例外
     */
    public SlackUnsignedRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause ネスト例外
     */
    public SlackUnsignedRequestException(Throwable cause) {
        super(cause);
    }

}
