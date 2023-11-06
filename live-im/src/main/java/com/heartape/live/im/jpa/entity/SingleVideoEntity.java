package com.heartape.live.im.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.uuid.UuidGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "live_message_single_video")
public class SingleVideoEntity {

    @Id
    @GenericGenerator(name = "idGenerator", type = UuidGenerator.class)
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
