package com.example.ci.cd;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TestServiceTest {
    private final TestService testService = new TestService();
    @Test
    public void hello() {
        Assertions.assertThat(testService.returnOne()).isEqualTo(1);
    }
    @Test
    public void two() {
        Assertions.assertThat(testService.returnOne()).isEqualTo(1);

    }

}