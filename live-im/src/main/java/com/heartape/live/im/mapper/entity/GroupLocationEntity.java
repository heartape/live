package com.heartape.live.im.mapper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.geom.Point2D;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("live_message_group_location")
public class GroupLocationEntity {

    /** id */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /** messageId */
    private String messageId;

    private Point2D.Double point;

    private String desc;

}
