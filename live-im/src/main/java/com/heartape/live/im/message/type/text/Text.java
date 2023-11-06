package com.heartape.live.im.message.type.text;

import com.heartape.live.im.message.Content;
import lombok.Getter;

/**
 * 文本
 * @since 0.0.1
 * @author heartape
 */
@Getter
public class Text implements Content {

    private String text;

}
