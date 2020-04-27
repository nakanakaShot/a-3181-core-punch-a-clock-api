package com.herokuapp.a3181core.punchaclockdev.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SlackRepositoryTest {

    @Autowired
    SlackRepository slackRepository;

    @Test
    void hogeTest() {
        slackRepository.hoge();
    }

}
