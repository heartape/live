package com.heartape.live.bullet.controller;

import com.heartape.live.bullet.connect.SpringSseConnection;
import com.heartape.live.bullet.manager.BulletManager;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@AllArgsConstructor
@RestController
@RequestMapping("/chat/connection")
public class BulletConnectionController {

    private final BulletManager bulletManager;

    /**
     * 分布式情况下采用一致性hash等方式让观众分配到固定的机器
     */
    @GetMapping("/sse")
    public SseEmitter create(@RequestParam String roomId, @RequestParam String uid){
        SseEmitter sseEmitter = new SseEmitter(300000L);
        this.bulletManager.register(new SpringSseConnection<>(uid, roomId, sseEmitter));
        return sseEmitter;
    }

    @DeleteMapping
    public void destroy(@RequestParam String roomId, @RequestParam String uid){
        this.bulletManager.logout(roomId, uid);
    }
}
