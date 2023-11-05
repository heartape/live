package com.heartape.live.im.message.type.image;

import lombok.Getter;

/**
 * 图片副本，包含各种规格的图片
 */
@Getter
public class ImageCopy {
    /** id */
    private String id;

    /**
     * 图片类型: 原图，大图，缩略图
     */
    private Integer type;

    private Integer size;

    private Integer width;

    private Integer height;

    private String url;

}
