package com.herokuapp.a3181core.punchaclockdev.shared;

import com.herokuapp.a3181core.punchaclockdev.configure.AppProperties;
import com.herokuapp.a3181core.punchaclockdev.exception.SlackAuthenticatorUnexpectedException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackAuthenticator {

    private static final String SLACK_APP_SIGNING_VERSION = "v0";
    private static final int MINUTE_AS_SECONDS = 60;

    private final ClockProvider clockProvider;
    private final AppProperties props;

    /**
     * <pre>
     * Slackからのリクエストか検証する
     * https://api.slack.com/authentication/verifying-requests-from-slack
     * </pre>
     *
     * @param request 検証リクエスト
     * @return Slackからのリクエストの場合 true
     */
    public boolean isSignedRequestFromSlack(
        HttpServletRequest request) {

        String queryString = getRawRequestBodyFromParameter(request);
        String slackRequestTimeStamp = request.getHeader("X-Slack-Request-Timestamp");
        String slackSignature = request.getHeader("X-Slack-Signature");

        // Slack認証キーを生成する準備
        String baseString = String.join(":",
            SLACK_APP_SIGNING_VERSION,
            slackRequestTimeStamp,
            queryString);

        String secret = props.getSlack().getAppSigningSecret();
        if (StringUtils.isEmpty(secret)) {
            log.error("Signing Secret is undefined.");
            throw new SlackAuthenticatorUnexpectedException();
        }

        if (isPastForFiveMinutes(slackRequestTimeStamp)) {
            return false;
        }

        return isSignedRequest(baseString, secret, slackSignature);


    }

    boolean isPastForFiveMinutes(String slackRequestTimeStamp) {
        long timeStamp;
        try {
            timeStamp = Long.parseLong(slackRequestTimeStamp);
        } catch (NumberFormatException ex) {
            return true;
        }
        long plusFiveMinutesFromTs = timeStamp + (5 * MINUTE_AS_SECONDS);

        return clockProvider.nowAsUnixTime() > plusFiveMinutesFromTs;
    }

    private boolean isSignedRequest(String baseString, String secret, String slackSignature) {
        // 暗号化ダイジェストを作成
        byte[] digest = new HMacSha256Digest(baseString, secret).digest();
        // 比較
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

    String getRawRequestBodyFromParameter(HttpServletRequest request) {
        return request.getParameterMap().entrySet().stream()
            .map(entry -> entry.getKey() + "=" + String
                .join(",", urlEncode(entry.getValue())))
            .collect(Collectors.joining("&"));
    }

    private List<String> urlEncode(String[] target) {
        return Arrays.stream(target).map(str -> {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                throw new SlackAuthenticatorUnexpectedException(ex);
            }
        }).collect(Collectors.toList());
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
                mac.init(new SecretKeySpec(
                    secret.getBytes(StandardCharsets.UTF_8),
                    SIGNING_CRYPT_ALGORITHM));
                return mac.doFinal(baseString.getBytes(StandardCharsets.UTF_8));

            } catch (InvalidKeyException | NoSuchAlgorithmException ex) {
                throw new SlackAuthenticatorUnexpectedException(ex);
            }
        }
    }

}
