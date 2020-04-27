package com.herokuapp.a3181core.punchaclockdev.shared;

import com.herokuapp.a3181core.punchaclockdev.configure.AppProperties;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SlackAuthenticator {

    private static final String SLACK_APP_SIGNING_VERSION = "v0";

    private final AppProperties props;

    /**
     * <pre>
     * Slackからのリクエストか検証する
     * https://api.slack.com/authentication/verifying-requests-from-slack
     * </pre>
     *
     * @param queryString           リクエストのクエリ文字列
     * @param slackRequestTimeStamp Slackがリクエストしたときのタイムスタンプ
     * @param slackSignature        Slackから受け取った署名(期待値)
     * @return Slackからのリクエストの場合 true
     */
    public boolean isSignedRequestFromSlack(
        String queryString,
        String slackRequestTimeStamp,
        String slackSignature) {

        // Slack認証キーを生成する準備
        String baseString = String.join(":",
            SLACK_APP_SIGNING_VERSION,
            slackRequestTimeStamp,
            queryString);
        String secret = props.getSlack().getAppSigningSecret();

        // 暗号化ダイジェストを作成
        byte[] digest = new HMacSha256Digest(baseString, secret).digest();

        return slackSignature.equals(generateChallengeToken(digest));
    }

    /**
     * 暗号化されたダイジェストからSlack認証キーを生成
     *
     * @param digest 暗号化されたダイジェスト
     * @return v0=digestの16進数
     */
    private String generateChallengeToken(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        sb.append(SLACK_APP_SIGNING_VERSION).append("=");

        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    @RequiredArgsConstructor
    private static class HMacSha256Digest {

        private static final String SIGNING_CRYPT_ALGORITHM = "HmacSHA256";

        private final String baseString;
        private final String secret;

        /**
         * HMacSha256で暗号化ダイジェストを作成する
         *
         * @return HMacSha256で暗号化されたダイジェスト
         */
        byte[] digest() {
            try {
                Mac mac = Mac.getInstance(SIGNING_CRYPT_ALGORITHM);
                mac.init(new SecretKeySpec(secret.getBytes(), SIGNING_CRYPT_ALGORITHM));
                return mac.doFinal(baseString.getBytes());

            } catch (InvalidKeyException | NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
