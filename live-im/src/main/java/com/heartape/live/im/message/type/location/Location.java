package com.heartape.live.im.message.type.location;

import com.heartape.live.im.message.base.Content;

import java.awt.geom.Point2D;

/**
 * 位置
 * @since 0.0.1
 * @author heartape
 */
public class Location implements Content {

    private Point2D.Double longitude;

    private Point2D.Double latitude;

    private String desc;

}
