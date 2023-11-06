package com.heartape.live.im.message.type.location;

import com.heartape.live.im.message.Content;
import lombok.Getter;

import java.awt.geom.Point2D;

/**
 * 位置
 * @since 0.0.1
 * @author heartape
 */
@Getter
public class Location implements Content {

    private Point2D.Double point;

    private String desc;

}
