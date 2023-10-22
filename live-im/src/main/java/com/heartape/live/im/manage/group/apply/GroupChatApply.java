package com.heartape.live.im.manage.group.apply;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 加入群聊申请
 */
@Getter
@Setter
@AllArgsConstructor
public class GroupChatApply {

    /**
     * id
     */
    private Long id;

    /**
     * uid
     */
    private String uid;

    /**
     * 群聊id
     */
    private Long groupId;

    /**
     * 申请时间
     */
    private LocalDateTime timestamp;

    /**
     * 申请结果
     * -1:拒绝
     * 0:尚未处理
     * 1:同意
     */
    private Integer result;
}
