package com.heartape.live.bullet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartape.live.bullet.repository.bullet.Bullet;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/chat")
@AllArgsConstructor
public class BulletController {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping
    public void send(@RequestParam String roomId, @RequestParam String uid, @RequestBody String message) {
        long timestamp = System.currentTimeMillis();
        Bullet bullet = new Bullet(UUID.randomUUID().toString(), uid, roomId,message, "type1", timestamp);
        ObjectMapper objectMapper = new ObjectMapper();
        String s;
        try {
            s = objectMapper.writeValueAsString(bullet);
        } catch (JsonProcessingException e) {
            return;
        }
        kafkaTemplate.send("live-chat", s);
    }

}
