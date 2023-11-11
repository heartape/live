package com.heartape.live.im.mapper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 图片副本，包含各种规格的图片
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("live_message_group_image_copy")
public class GroupImageCopyEntity {

    /** id */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /** imageId */
    private String imageId;

    /**
     * 图片类型: 原图，大图，缩略图
     */
    private Integer type;

    private Integer size;

    private Integer width;

    private Integer height;

    private String url;

}
