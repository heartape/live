package com.heartape.live.im.message.type.greeting;

import com.heartape.live.im.message.base.Content;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 文本
 * @since 0.0.1
 * @author heartape
 */
@NoArgsConstructor
@AllArgsConstructor
public class Greeting implements Content {

    @Getter
    private String greetings;

}
