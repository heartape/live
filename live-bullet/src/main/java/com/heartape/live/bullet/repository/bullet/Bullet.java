package com.heartape.live.bullet.repository.bullet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heartape.live.bullet.flow.FlowElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bullet implements Serializable, FlowElement {
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

    @JsonIgnore
    @Override
    public String getDestination() {
        return roomId;
    }
}
