package com.heartape.live.im.manage.friend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 好友关系
 */
@Getter
@Setter
@AllArgsConstructor
public class Friendship {

    /**
     * id
     */
    private Long id;

    /**
     * 分组id
     */
    private Long groupId;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 好友用户id
     */
    private String friendId;
    /**
     * 好友关系类型
     * -1: 拉黑
     * 1: 正常
     */
    private Integer type;

}
