package com.heartape.live.bullet.repository.bullet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * todo:抽象出来，并且将这个目录拆分，readme完善
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bullet implements Serializable {
    @Serial
    private static final long serialVersionUID = -6020546094258938952L;
    private String id;
    /** 用户id */
    private String uid;
    /** 房间id */
    private String roomId;
    private String content;
    /** 样式 */
    private String type;
    private Long timestamp;
}
