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
@Table(name = "live_message_sound")
public class SingleSoundEntity {

    /** id */
    @Id
    @GenericGenerator(name = "idGenerator", type = UuidGenerator.class)
    private String id;

    /** messageId */
    private String messageId;

    /** 下载路径 */
    private String url;

    /** 文件大小 */
    private Integer size;

    /** 时长 */
    private Integer second;

}
