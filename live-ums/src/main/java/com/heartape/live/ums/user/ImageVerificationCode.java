package com.heartape.live.ums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.image.BufferedImage;

/**
 * 验证码
 */
@Getter
@AllArgsConstructor
public class ImageVerificationCode implements VerificationCode {

    private String id;
    private String text;
    private BufferedImage image;
    private long expireTime;

    @Override
    public String getTag() {
        return null;
    }
}
