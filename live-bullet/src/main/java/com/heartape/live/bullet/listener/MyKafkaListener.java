package com.heartape.live.bullet.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartape.live.bullet.constant.KafkaConstant;
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
            id = "live-bullet",
            clientIdPrefix = "live-bullet-consumer",
            groupId = "live-group",
            topics = KafkaConstant.TOPIC,
            batch = "true"
    )
    public void batch(List<String> bulletStrList) {
        if (bulletStrList != null && !bulletStrList.isEmpty()){
            List<Bullet> bullets = new ArrayList<>();
            for (String bulletStr : bulletStrList) {
                Bullet bullet;
                try {
                    bullet = objectMapper.readValue(bulletStr, Bullet.class);
                    bullets.add(bullet);
                } catch (JsonProcessingException ignore) {}
            }
            log.debug("接收{}条消息", bullets.size());
            bulletManager.push(bullets);
        }
    }
}
