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
@Table(name = "live_message_group_greeting")
public class GroupGreetingEntity {

    @Id
    @GenericGenerator(name = "idGenerator", type = UuidGenerator.class)
    private String id;

    /** messageId */
    private String messageId;

    @Getter
    private String greetings;

}
