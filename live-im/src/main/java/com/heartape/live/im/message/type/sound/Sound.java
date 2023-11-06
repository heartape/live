package com.heartape.live.im.message.type.sound;

import com.heartape.live.im.message.Content;
import lombok.Getter;

/**
 * 音频
 * @since 0.0.1
 * @author heartape
 */
@Getter
public class Sound implements Content {

    /** 下载路径 */
    private String url;

    /** 文件大小 */
    private Integer size;

    /** 时长 */
    private Integer second;

}
