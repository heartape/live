package com.heartape.live.im.manage.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 群聊
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupChatMember {

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
     * 角色
     */
    private Integer role;

    /**
     * 类型
     * -2:拉黑
     * -1:禁言
     * 0:删除
     * 1:正常
     */
    private Integer type;

}
