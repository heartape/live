package com.heartape.live.ums.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.uuid.UuidGenerator;

import java.time.LocalDateTime;

/**
 * 用户
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

    /** id 主键*/
    @Id
    @GenericGenerator(name = "idGenerator", type = UuidGenerator.class)
    private String id;
    private String avatar;
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private String email;

    @Column(name = "phone_verified")
    private Boolean isPhoneVerified;
    @Column(name = "email_verified")
    private Boolean isEmailVerified;

    @Convert(converter = UserStatusConverter.class)
    private UserStatus status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
