package com.heartape.live.im.mapper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("live_message_group_greeting")
public class GroupGreetingEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /** messageId */
    private String messageId;

    @Getter
    private String greetings;

}
