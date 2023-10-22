package com.heartape.live.im.message.type.image;

import com.heartape.live.im.message.base.Content;
import lombok.Getter;

import java.util.List;

/**
 * 图片
 * @since 0.0.1
 * @author heartape
 */
@Getter
public class Image implements Content {

    /** id */
    private String id;

    /** 图片格式 */
    private String format;

    private List<ImageCopy> imageCopyList;

    /**
     * 图片副本，包含各种规格的图片
     */
    @Getter
    public static class ImageCopy {
        /**
         * 图片类型: 原图，大图，缩略图
         */
        private Integer type;

        private Integer size;

        private Integer width;

        private Integer height;

        private String url;
    }

}
