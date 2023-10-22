package com.heartape.live.im.manage.group.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 查看群聊消息列表简要信息
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupChatSimpleInfo {
    /**
     * id
     */
    private Long id;

    /**
     * 群聊名称
     */
    private String name;

    /**
     * 群聊头像
     */
    private String avatar;
}
