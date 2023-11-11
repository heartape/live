package com.heartape.live.im.mapper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName(value = "live_message_group", autoResultMap = true)
public class GroupEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /** 发送者id */
    protected String uid;

    /** 消息对象id */
    protected String groupId;

    /** 消息类型 */
    protected String type;

    /** 已读人数 */
    protected Integer receipt;

    /** 发送时间 */
    protected LocalDateTime time;

}
