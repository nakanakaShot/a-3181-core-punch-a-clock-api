package com.herokuapp.a3181core.punchaclockdev.infrastructure;

import com.herokuapp.a3181core.punchaclockdev.domain.model.SlackParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SlackRepositoryTest {

    @Autowired
    SlackRepository slackRepository;

    SlackParam param = new SlackParam();

    @Test
    void hogeTest() {
        param.setUserName("Tarou");
        slackRepository.postParam(param);
    }

}
