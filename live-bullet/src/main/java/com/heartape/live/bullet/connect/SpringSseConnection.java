package com.heartape.live.bullet.connect;

import lombok.Getter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Getter
public class SpringSseConnection<T> implements Connection<T> {
    private final String uid;
    private final String roomId;
    private final SseEmitter sseEmitter;

    private volatile boolean completed = false;

    public SpringSseConnection(String uid, String roomId, SseEmitter sseEmitter) {
        this.uid = uid;
        this.roomId = roomId;
        this.sseEmitter = sseEmitter;
        this.sseEmitter.onCompletion(() -> this.completed = true);
        this.sseEmitter.onTimeout(() -> this.completed = true);
        this.sseEmitter.onError(e -> this.completed = true);
    }

    @Override
    public void send(List<T> list, long timestamp) throws IOException {
        String timestampStr = Long.toString(timestamp);
        sseEmitter.send(SseEmitter.event()
                .id(timestampStr)
                .data(list)
        );
    }
    @Override
    public void disconnect() {
        this.completed = true;
        sseEmitter.complete();
    }
}
