package com.wjy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.wjy.entity.dto.RestBean;
import com.wjy.entity.pojo.User;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String appName;

    @PostMapping("login")
    public ResponseEntity<RestBean<?>> login(@RequestBody User user){
        ServiceInstance serviceInstance = loadBalancerClient.choose("auth-provider");
        String url = String.format("http://%s:%s/api/auth/login", serviceInstance.getHost(), serviceInstance.getPort());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> reqEntity = new HttpEntity<User>(user,headers);
        try{
            ResponseEntity<RestBean> resp = restTemplate.postForEntity(url, reqEntity, RestBean.class);
            if (resp.getBody().getStatus() == 20000) {
                return ResponseEntity.ok(resp.getBody());
            }else{
                return new ResponseEntity<RestBean<?>>(RestBean.failure(10086,resp.getBody().getMessage()),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
                return new ResponseEntity<RestBean<?>>(RestBean.failure(10001, e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/echo/auth")
    public String echoAppName() {
        // 使用 LoadBalanceClient 和 RestTemplate 结合的方式来访问
        ServiceInstance serviceInstance = loadBalancerClient.choose("auth");
        String path = "out/service";
        String url = String.format("http://%s:%s/api/echo/%s", serviceInstance.getHost(), serviceInstance.getPort(),path);
        System.out.println("request url:" + url);
        return restTemplate.getForObject(url, String.class);
    }

}
