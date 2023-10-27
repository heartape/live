package com.heartape.live.bullet.config;

import com.heartape.live.bullet.constant.KafkaConstant;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Slf4j
@AllArgsConstructor
@Configuration
public class KafkaInitConfiguration {

    private final AdminClient adminClient;

    @PostConstruct
    public void init() {
        ListTopicsResult listTopicsResultBefore = adminClient.listTopics();
        Map<String, TopicListing> topicsBefore;
        try {
            topicsBefore = listTopicsResultBefore.namesToListings().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        if (topicsBefore.containsKey(KafkaConstant.TOPIC)) {
            log.info("topic已存在，详情：\n{}", topicsBefore.get(KafkaConstant.TOPIC));
            return;
        }
        NewTopic topic = new NewTopic(KafkaConstant.TOPIC, 3, (short) 2);
        adminClient.createTopics(Collections.singleton(topic));
        Set<String> nameSet;
        try {
            nameSet = listTopicsResultBefore.names().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        log.info("创建后topic列表：\n{}", nameSet);
    }

}
