package com.wjy.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wjy.auth.entity.dto.RestBean;
import com.wjy.auth.entity.pojo.User;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    @PostMapping("login")
    public RestBean<User> login(@RequestBody User user){
        if (user.getUsername().equals("123")) return RestBean.success("login successed",user);
        else return RestBean.failure(50501, "用户名错了傻逼");
    }
    
}
