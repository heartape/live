package com.heartape.live.im.message.type.video;

import com.heartape.live.im.message.Content;
import lombok.Getter;

/**
 * 视频
 * @since 0.0.1
 * @author heartape
 */
@Getter
public class Video implements Content {

    /** 下载路径 */
    private String url;
    /** 视频格式 */
    private String format;
    /** 文件大小 */
    private Integer size;
    /** 时长 */
    private Integer second;
    /** 缩略图 */
    private String thumbId;
    /** 缩略图格式：jpg、png、gif */
    private String thumbFormat;
    /** 缩略图url */
    private String thumbUrl;
    /** 缩略图宽 */
    private Integer thumbWidth;
    /** 缩略图高 */
    private Integer thumbHeight;
}
