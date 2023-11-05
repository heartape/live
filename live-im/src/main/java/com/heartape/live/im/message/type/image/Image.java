package com.heartape.live.im.message.type.image;

import com.heartape.live.im.message.Content;
import lombok.Getter;

import java.util.List;

/**
 * 图片
 * @since 0.0.1
 * @author heartape
 */
@Getter
public class Image implements Content {

    /** 图片名 */
    private String name;

    /** 图片格式 */
    private String format;

    private List<ImageCopy> imageCopyList;

}
