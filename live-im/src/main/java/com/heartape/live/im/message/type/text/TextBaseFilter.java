package com.heartape.live.im.message.type.text;

import com.heartape.live.im.message.filter.Filter;
import com.heartape.live.im.send.ErrorSend;
import com.heartape.live.im.send.Send;

/**
 * 文字类型消息基本过滤器
 * @since 0.0.1
 * @author heartape
 */
public class TextBaseFilter implements Filter<TextMessage> {

    @Override
    public Send doFilter(TextMessage textMessage) {
        Text text = textMessage.getContent();
        String textText = text.getText();
        if (textText == null || textText.isBlank()){
            return new ErrorSend("MESSAGE IS BLANK");
        }
        if (textText.length() > 10000){
            return new ErrorSend("MESSAGE IS TOO LONG, MAX LENGTH IS 10000");
        }
        return null;
    }
}
