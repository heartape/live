package com.heartape.live.ums.user;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import java.awt.image.BufferedImage;
import java.util.Properties;
import java.util.Random;

/**
 * 由于验证码的生成比较耗时，所以将其缓存起来。在被使用了一定次数后清理。
 */
public class CachedImageVerificationCodeFactory implements VerificationCodeFactory {

    private final ImageVerificationCode[] codes = new ImageVerificationCode[1024];
    private final byte[] counts = new byte[1024];

    /** 允许重复使用的次数 */
    private final static int count = 5;

    private final Random random = new Random();

    private final DefaultKaptcha kaptchaProducer;

    public CachedImageVerificationCodeFactory() {
        this.kaptchaProducer = new DefaultKaptcha();
        init();
    }

    private void init() {
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.textproducer.font.color", "black");
        properties.setProperty("kaptcha.textproducer.char.space", "4");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.char.string", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        Config config = new Config(properties);
        kaptchaProducer.setConfig(config);
    }

    @Override
    public synchronized VerificationCode next() {
        int i = 0;
        for (int j = 0; j < count; j++) {
            i = random.nextInt(1024);
            if (counts[i] <= count) {
                break;
            }
            codes[i] = null;
            counts[i] = 0;
        }

        if (codes[i] == null) {
            ImageVerificationCode imageVerificationCode = generate();
            codes[i] = imageVerificationCode;
            counts[i] = 1;
            return imageVerificationCode;
        }
        counts[i]++;
        return codes[i];
    }

    private ImageVerificationCode generate() {
        // 生成文字验证码
        String text = kaptchaProducer.createText();
        // 生成图片验证码
        BufferedImage image = kaptchaProducer.createImage(text);
        return new ImageVerificationCode(text, image, 60);
    }
}
