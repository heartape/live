package com.heartape.live.bullet.connect;

import lombok.Getter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * Spring sse 长连接
 * @see Connection
 * @see SseEmitter
 */
@Getter
public class SpringSseConnection implements Connection {
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
    public void send(Object o, long timestamp) {
        String timestampStr = Long.toString(timestamp);
        try {
            sseEmitter.send(SseEmitter.event()
                    .id(timestampStr)
                    .data(o)
            );
        } catch (IOException ignored) {
            // 不再发送消息，直接断开连接。
            disconnect();
        }
    }
    @Override
    public void disconnect() {
        this.completed = true;
        sseEmitter.complete();
    }
}
