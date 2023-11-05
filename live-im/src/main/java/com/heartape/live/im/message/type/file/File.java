package com.heartape.live.im.message.type.file;

import com.heartape.live.im.message.Content;

/**
 * 文件
 * @since 0.0.1
 * @author heartape
 */
public class File implements Content {

    /** 下载路径 */
    private String url;

    /** 下载路径 */
    private String filename;

    /** 文件大小 */
    private Integer size;

}
