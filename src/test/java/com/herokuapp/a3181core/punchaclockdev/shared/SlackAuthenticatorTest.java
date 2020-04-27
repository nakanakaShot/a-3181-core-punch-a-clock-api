package com.herokuapp.a3181core.punchaclockdev.shared;

import static org.mockito.Mockito.when;

import com.herokuapp.a3181core.punchaclockdev.configure.AppProperties;
import com.herokuapp.a3181core.punchaclockdev.configure.AppProperties.Slack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SlackAuthenticatorTest {

    @Autowired
    private SlackAuthenticator target;

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

        String queryString = "token=xyzz0WbapA4vBCDEFasx0q6G&team_id=T1DC2JH3J&team_domain=testteamnow&channel_id=G8PSS9T3V&channel_name=foobar&user_id=U2CERLKJA&user_name=roadrunner&command=%2Fwebhook-collect&text=&response_url=https%3A%2F%2Fhooks.slack.com%2Fcommands%2FT1DC2JH3J%2F397700885554%2F96rGlfmibIGlgcZRskXaIFfN&trigger_id=398738663015.47445629121.803a0bc887a14d10d2c447fce8b6703c";
        String timeStamp = "1531420618";
        String expect = "v0=a2114d57b48eac39b9ad189dd8316235a7b4a8d21a10bd27519666489c69b503";

        Assertions.assertTrue(target.isSignedRequestFromSlack(queryString, timeStamp, expect));
    }
}
