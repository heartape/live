package com.heartape.live.im.manage.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 群聊
 */
@Getter
@Setter
@AllArgsConstructor
public class GroupChat {

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

    /**
     * 级别
     * 1：100人
     * 2：200人
     * 3：500人
     * 4：1000人
     * 5：2000人
     */
    private Integer level;

    /**
     * 认证方式
     * 0：不需要认证信息
     * 1：需要认证信息（对加群动机的一段描述）
     * 2：需要回答问题（系统判断是否合法）
     * 3：需要回答问题（由群管理员判断是否合法）
     */
    private Integer authMode;

    /**
     * 标签
     */
    private List<String> tags;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
