package com.wjy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Flyinsky
 * @date 2025/3/31 17:20
 * @email: w2084151024@gmail.com
 */
@RestController
@RequestMapping("/api/echo")
public class EchoController {
    @GetMapping("out/{string}")
    public String echo(@PathVariable String string) {
        return "Hello Nacos" + string;
    }
}
