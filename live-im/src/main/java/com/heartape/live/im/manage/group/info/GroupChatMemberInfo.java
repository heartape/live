package com.heartape.live.im.manage.group.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 群聊成员简要信息
 */
@Getter
@Setter
@AllArgsConstructor
public class GroupChatMemberInfo {

    /**
     * uid
     */
    private String uid;

    /**
     * 角色
     */
    private Integer role;

}
