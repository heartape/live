package com.heartape.live.bullet.controller;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;


class BulletControllerTest {

    @Test
    void send() {
        RestTemplate restTemplate  = new RestTemplate();
        String message = "测试数据";
        for (int i = 1; i <= 100; i++) {
            restTemplate.postForObject("http://localhost:8002/bullet?roomId=114514&uid=1111", message + i, Void.class);
        }
    }

    @Test
    void get() {
    }
}