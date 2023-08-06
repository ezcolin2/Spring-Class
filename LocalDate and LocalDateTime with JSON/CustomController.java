package com.formatting.jsonlocaldate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomController {
    @GetMapping("/date")
    public ObjectEx hello(@RequestBody ObjectEx objectEx) {
        return objectEx;
    }
}
