package com.herokuapp.a3181core.punchaclockdev.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.herokuapp.a3181core.punchaclockdev.exception.SlackAuthenticatorUnexpectedException;
import com.herokuapp.a3181core.punchaclockdev.shared.SlackAuthenticator;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class SlackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SlackController controller;

    @MockBean
    private SlackAuthenticator slackAuthenticator;

    @BeforeEach
    void setUp() {
        when(slackAuthenticator.isSignedRequestFromSlack(any())).thenReturn(true);
    }

    /**
     * /slack/attendの正常系テスト
     * <p>
     * parameterizedTest化したいときは、paramのvaluesを変数にするとよいです
     */
    @Test
    void attendPostTest() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/slack/attend")
                .param("token", "gIkuvaNzQIHg97ATvDxqgjtO")
                .param("team_id", "T0001")
                .param("team_domain", "example")
                .param("enterprise_id", "E0001")
                .param("enterprise_name", "Globular%20Construct%20Inc")
                .param("channel_id", "C2147483705")
                .param("channel_name", "test")
                .param("user_id", "U2147483697")
                .param("user_name", "Steve")
                .param("command", "%2Fweather")
                .param("text", "94070")
                .param("response_url", "https%3A%2F%2Fhooks.slack.com%2Fcommands%2F1234%2F5678")
                .param("trigger_id", "13345224609.738474920 .8088930838d 88f 008e0")

        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content()
                .string("出勤！おはようございます\uD83C\uDF1E"));
    }

    @Test
    void attendGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/slack/attend"))
            .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    void breakPostTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/slack/break"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content()
                .string("休憩開始！リラックスしましょう\uD83D\uDE0A"));
    }

    @Test
    void breakGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/slack/return"))
            .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    void returnPostTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/slack/return"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content()
                .string("休憩終了！適度に頑張りましょう\uD83C\uDFC3\u200D♂️"));
    }

    @Test
    void returnGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/slack/return"))
            .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    void dismissPostTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/slack/dismiss"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content()
                .string("退勤！お疲れさまでした \uD83D\uDECF"));
    }

    @Test
    void dismissGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/slack/dismiss"))
            .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    void listPostTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/slack/list"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content()
                .string("hello, world"));
    }

    @Test
    void listGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/slack/list"))
            .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    void errorIfUnsignedSlackRequest() throws Exception {
        when(slackAuthenticator.isSignedRequestFromSlack(any())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/slack/attend"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content()
                .string("投稿に失敗しました。"));
    }

    private static Stream<Arguments> slackAuthenticatorExceptionProvider() {
        return Stream.of(
            Arguments.of(SlackAuthenticatorUnexpectedException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("slackAuthenticatorExceptionProvider")
    void errorFromSlackAuthenticator(Class<Throwable> clazz) throws Exception {
        when(slackAuthenticator.isSignedRequestFromSlack(any())).thenThrow(clazz);

        mockMvc.perform(MockMvcRequestBuilders.post("/slack/attend"))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError())
            .andExpect(MockMvcResultMatchers.content()
                .string("サーバーにアクセスできませんでした。"));
    }
}
