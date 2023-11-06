package com.heartape.live.im.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.uuid.UuidGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "live_message_single")
public class SingleEntity {

    @Id
    @GenericGenerator(name = "idGenerator", type = UuidGenerator.class)
    private String id;

    /** 发送者id */
    protected String uid;

    /** 消息对象id */
    protected String receiverId;

    /** 消息类型 */
    protected String type;

    /** 消息是否已读 */
    protected Boolean read;

    /** 发送时间 */
    protected LocalDateTime timestamp;

}