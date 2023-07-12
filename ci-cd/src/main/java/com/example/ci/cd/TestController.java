package com.example.ci.cd;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    public int hello() {
        return testService.returnOne();
    }

    @GetMapping("/")
    public String hei() {
        return "hello";
    }

    @GetMapping("/hello")
    public String helo() {
        return "hello ci-cd!";
    }

    @GetMapping("/hihi")
    public String hihi() {
        return "lssdfhasdgh";
    }

    @GetMapping("/question")
    public String question() {
        String hi = null;
        hi.getBytes();
        return "queston123235";
    }
}
