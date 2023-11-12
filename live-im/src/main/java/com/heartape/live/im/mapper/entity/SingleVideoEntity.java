package com.heartape.live.im.mapper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("live_message_single_video")
public class SingleVideoEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    /** messageId */
    private String messageId;
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
