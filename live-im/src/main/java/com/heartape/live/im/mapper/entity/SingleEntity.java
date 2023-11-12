package com.heartape.live.im.mapper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "live_message_single", autoResultMap = true)
public class SingleEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /** 发送者id */
    private String uid;

    /** 消息对象id */
    private String receiverId;

    /** 消息类型 */
    private String type;

    /** 消息是否已读 */
    @TableField("`read`")
    private Boolean read;

    /** 发送时间 */
    private LocalDateTime time;

}
