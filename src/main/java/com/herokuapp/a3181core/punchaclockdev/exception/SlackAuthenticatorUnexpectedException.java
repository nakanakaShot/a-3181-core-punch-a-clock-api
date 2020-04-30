package com.herokuapp.a3181core.punchaclockdev.exception;

public class SlackAuthenticatorUnexpectedException extends RuntimeException {

    private static final long serialVersionUID = 7656826961398728357L;

    /**
     * Slack認証予期せぬ例外
     */
    public SlackAuthenticatorUnexpectedException() {

    }

    /**
     * @param message エラーメッセージ
     */
    public SlackAuthenticatorUnexpectedException(String message) {
        super(message);
    }

    /**
     * @param message エラーメッセージ
     * @param cause   ネスト例外
     */
    public SlackAuthenticatorUnexpectedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause ネスト例外
     */
    public SlackAuthenticatorUnexpectedException(Throwable cause) {
        super(cause);
    }

}
