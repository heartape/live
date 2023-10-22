package com.heartape.live.bullet.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartape.live.bullet.manager.BulletManager;
import com.heartape.live.bullet.repository.bullet.Bullet;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class MyKafkaListener {

    private final BulletManager bulletManager;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(
            id = "live-chat",
            clientIdPrefix = "live-chat-consumer",
            groupId = "live-group",
            topics = "live-chat",
            batch = "true"
    )
    public void batch(List<String> bulletChatStrList) {
        if (bulletChatStrList != null && !bulletChatStrList.isEmpty()){
            List<Bullet> bullets = new ArrayList<>();
            for (String bulletChatStr : bulletChatStrList) {
                Bullet bullet;
                try {
                    bullet = objectMapper.readValue(bulletChatStr, Bullet.class);
                    bullets.add(bullet);
                } catch (JsonProcessingException ignore) {}
            }
            log.debug("接收{}条消息", bullets.size());
            bulletManager.push(bullets);
        }
    }
}
