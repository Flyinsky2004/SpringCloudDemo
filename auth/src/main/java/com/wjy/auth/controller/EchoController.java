package com.wjy.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/echo/")
public class EchoController {
    @GetMapping("out/{string}")
    public String echo(@PathVariable String string) {
        return "Hello Nacos" + string;
    }
}
