package com.herokuapp.a3181core.punchaclockdev.shared;

import static org.mockito.Mockito.when;

import com.herokuapp.a3181core.punchaclockdev.configure.AppProperties;
import com.herokuapp.a3181core.punchaclockdev.configure.AppProperties.Slack;
import com.herokuapp.a3181core.punchaclockdev.exception.SlackAuthenticatorUnexpectedException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SlackAuthenticatorTest {

    private static final int MINUTE_AS_SECONDS = 60;
    private static final long BASE_SECONDS = 1000000000L;

    @Autowired
    private SlackAuthenticator target;

    @MockBean
    private ClockProvider clockProvider;

    @MockBean
    private AppProperties props;

    @Test
    void normal() {
        /*
         * <pre>
         * チェック用データはこちらを参考にした
         * https://api.slack.com/authentication/verifying-requests-from-slack
         * </pre>
         */

        Slack slack = new Slack("8f742231b10e8888abcd99yyyzzz85a5", null, null);
        when(props.getSlack()).thenReturn(slack);
        when(clockProvider.nowAsUnixTime()).thenReturn(1531420618L);

        Map<String, String[]> body = new LinkedHashMap<>();
        body.put("token", new String[]{"xyzz0WbapA4vBCDEFasx0q6G"});
        body.put("team_id", new String[]{"T1DC2JH3J"});
        body.put("team_domain", new String[]{"testteamnow"});
        body.put("channel_id", new String[]{"G8PSS9T3V"});
        body.put("channel_name", new String[]{"foobar"});
        body.put("user_id", new String[]{"U2CERLKJA"});
        body.put("user_name", new String[]{"roadrunner"});
        body.put("command", new String[]{"/webhook-collect"});
        body.put("text", new String[]{""});
        body.put("response_url", new String[]{
            "https://hooks.slack.com/commands/T1DC2JH3J/397700885554/96rGlfmibIGlgcZRskXaIFfN"});
        body.put("trigger_id",
            new String[]{"398738663015.47445629121.803a0bc887a14d10d2c447fce8b6703c"});

        String timeStamp = "1531420618";
        String expect = "v0=a2114d57b48eac39b9ad189dd8316235a7b4a8d21a10bd27519666489c69b503";

        MockHttpServletRequest input = new MockHttpServletRequest();
        input.setParameters(body);
        input.addHeader("X-Slack-Request-Timestamp", timeStamp);
        input.addHeader("X-Slack-Signature", expect);

        Assertions.assertTrue(target.isSignedRequestFromSlack(input));
    }

    @Test
    void errorCauseNoSigningSecret() {
        /*
         * <pre>
         * チェック用データはこちらを参考にした
         * https://api.slack.com/authentication/verifying-requests-from-slack
         * </pre>
         */

        Slack slack = new Slack(null, null, null);
        when(props.getSlack()).thenReturn(slack);
        when(clockProvider.nowAsUnixTime()).thenReturn(1531420618L);

        Map<String, String[]> body = new LinkedHashMap<>();
        body.put("token", new String[]{"xyzz0WbapA4vBCDEFasx0q6G"});
        body.put("team_id", new String[]{"T1DC2JH3J"});
        body.put("team_domain", new String[]{"testteamnow"});
        body.put("channel_id", new String[]{"G8PSS9T3V"});
        body.put("channel_name", new String[]{"foobar"});
        body.put("user_id", new String[]{"U2CERLKJA"});
        body.put("user_name", new String[]{"roadrunner"});
        body.put("command", new String[]{"/webhook-collect"});
        body.put("text", new String[]{""});
        body.put("response_url", new String[]{
            "https://hooks.slack.com/commands/T1DC2JH3J/397700885554/96rGlfmibIGlgcZRskXaIFfN"});
        body.put("trigger_id",
            new String[]{"398738663015.47445629121.803a0bc887a14d10d2c447fce8b6703c"});

        String timeStamp = "1531420618";
        String expect = "v0=a2114d57b48eac39b9ad189dd8316235a7b4a8d21a10bd27519666489c69b503";

        MockHttpServletRequest input = new MockHttpServletRequest();
        input.setParameters(body);
        input.addHeader("X-Slack-Request-Timestamp", timeStamp);
        input.addHeader("X-Slack-Signature", expect);

        Assertions.assertThrows(SlackAuthenticatorUnexpectedException.class,
            () -> target.isSignedRequestFromSlack(input));
    }

    private static Stream<Arguments> epochSecondsProvider() {
        long fiveMinutes = (5 * MINUTE_AS_SECONDS);
        return Stream.of(
            Arguments.of(BASE_SECONDS, BASE_SECONDS, false),
            Arguments.of(BASE_SECONDS, BASE_SECONDS + fiveMinutes, false),
            Arguments.of(BASE_SECONDS, BASE_SECONDS + fiveMinutes - 1, false),
            Arguments.of(BASE_SECONDS, BASE_SECONDS + fiveMinutes + 1, true)

        );
    }

    @ParameterizedTest
    @MethodSource("epochSecondsProvider")
    void isPastForFiveMinutesPattern(long input, long now, boolean expect) {
        when(clockProvider.nowAsUnixTime()).thenReturn(now);
        Assertions.assertEquals(expect, target.isPastForFiveMinutes(String.valueOf(input)));
    }

    @Test
    void isSuccessfullyUrlEncoded() {

        Map<String, String[]> body = new LinkedHashMap<>();
        body.put("token", new String[]{"xyzz0WbapA4vBCDEFasx0q6G"});
        body.put("team_id", new String[]{"T1DC2JH3J"});
        body.put("team_domain", new String[]{"testteamnow"});
        body.put("channel_id", new String[]{"G8PSS9T3V"});
        body.put("channel_name", new String[]{"foobar"});
        body.put("user_id", new String[]{"U2CERLKJA"});
        body.put("user_name", new String[]{"roadrunner"});
        body.put("command", new String[]{"/webhook-collect"});
        body.put("text", new String[]{""});
        body.put("response_url", new String[]{
            "https://hooks.slack.com/commands/T1DC2JH3J/397700885554/96rGlfmibIGlgcZRskXaIFfN"});
        body.put("trigger_id",
            new String[]{"398738663015.47445629121.803a0bc887a14d10d2c447fce8b6703c"});

        MockHttpServletRequest input = new MockHttpServletRequest();
        input.setParameters(body);

        String expected = "token=xyzz0WbapA4vBCDEFasx0q6G&team_id=T1DC2JH3J&team_domain=testteamnow&channel_id=G8PSS9T3V&channel_name=foobar&user_id=U2CERLKJA&user_name=roadrunner&command=%2Fwebhook-collect&text=&response_url=https%3A%2F%2Fhooks.slack.com%2Fcommands%2FT1DC2JH3J%2F397700885554%2F96rGlfmibIGlgcZRskXaIFfN&trigger_id=398738663015.47445629121.803a0bc887a14d10d2c447fce8b6703c";

        Assertions.assertEquals(expected, target.getRawRequestBodyFromParameter(input));
    }
}
