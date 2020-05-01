package com.herokuapp.a3181core.punchaclockdev.exception;

public class SlackApiPostUnexpectedException extends RuntimeException {

    private static final long serialVersionUID = -880986372227409255L;

    /**
     * Slack認証予期せぬ例外
     */
    public SlackApiPostUnexpectedException() {

    }

    /**
     * @param message エラーメッセージ
     */
    public SlackApiPostUnexpectedException(String message) {
        super(message);
    }

    /**
     * @param message エラーメッセージ
     * @param cause   ネスト例外
     */
    public SlackApiPostUnexpectedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause ネスト例外
     */
    public SlackApiPostUnexpectedException(Throwable cause) {
        super(cause);
    }

}
