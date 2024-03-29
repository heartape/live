package com.heartape.live.im.message.base;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.heartape.live.im.message.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 抽象基类
 * @since 0.0.1
 * @author heartape
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseMessage<T> implements Message {
    public BaseMessage(String id, String uid, String purpose, String purposeType, String type, Integer receipt, LocalDateTime timestamp) {
        this.id = id;
        this.uid = uid;
        this.purpose = purpose;
        this.purposeType = purposeType;
        this.type = type;
        this.receipt = receipt;
        this.timestamp = timestamp;
    }

    /** 消息id */
    @Setter
    protected String id;

    /** 发送人id */
    @Setter
    protected String uid;

    /** 消息对象id */
    protected String purpose;

    /** 消息对象类型 */
    protected String purposeType;

    /** 消息类型 */
    protected String type;

    /** 已读人数 */
    protected Integer receipt;

    /** 发送时间 */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    protected LocalDateTime timestamp;

    protected T content;
}
