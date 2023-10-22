package com.heartape.live.im.manage.friend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 好友分组
 */
@Getter
@Setter
@AllArgsConstructor
public class FriendshipGroup {
    /**
     * id
     */
    private Long id;
    /**
     * 用户id
     */
    private String uid;
    /**
     * 分组名称
     */
    private String name;
}
