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

/**
 * 图片副本，包含各种规格的图片
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "live_message_image_copy")
public class SingleImageCopyEntity {

    /** id */
    @Id
    @GenericGenerator(name = "idGenerator", type = UuidGenerator.class)
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
