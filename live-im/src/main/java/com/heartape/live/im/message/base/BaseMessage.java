package com.heartape.live.im.message.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
public class BaseMessage implements Message {

    /** 消息id */
    @Setter
    @JsonSerialize(using = ToStringSerializer.class)
    protected Long id;

    /** 发送人id */
    @Setter
    protected String uid;

    /** 消息对象id */
    protected String purpose;

    /** 消息对象类型 */
    protected String purposeType;

    /** 消息类型 */
    protected String type;

    /** 发送时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime timestamp;

    @Override
    public Object getContent() {
        return null;
    }
}
