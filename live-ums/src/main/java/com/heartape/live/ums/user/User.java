package com.heartape.live.ums.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    private Boolean isPhoneVerified;
    private Boolean isEmailVerified;

    private UserStatus status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
