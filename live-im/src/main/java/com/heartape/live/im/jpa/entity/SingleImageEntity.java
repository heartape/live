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
@Table(name = "live_message_single_image")
public class SingleImageEntity {

    /** id */
    @Id
    @GenericGenerator(name = "idGenerator", type = UuidGenerator.class)
    private String id;

    /** messageId */
    private String messageId;

    /** 图片名 */
    private String name;

    /** 图片格式 */
    private String format;

}
