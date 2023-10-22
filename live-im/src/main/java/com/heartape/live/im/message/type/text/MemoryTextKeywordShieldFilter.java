package com.heartape.live.im.message.type.text;

import com.heartape.live.im.message.filter.Filter;
import com.heartape.live.im.send.ErrorSend;
import com.heartape.live.im.send.Send;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 关键词屏蔽-内存实现（推荐使用搜索引擎）
 * @since 0.0.1
 * @author heartape
 */
public class MemoryTextKeywordShieldFilter implements Filter<TextMessage> {

    private final Set<String> keywordSet;

    public MemoryTextKeywordShieldFilter(Set<String> keywordSet) {
        this.keywordSet = Objects.requireNonNullElseGet(keywordSet, HashSet::new);
    }

    @Override
    public Send doFilter(TextMessage textMessage) {
        String text = textMessage.getContent().getText();
        for (String keyword : this.keywordSet) {
            if (text.contains(keyword)){
                return new ErrorSend("Contains sensitive vocabulary");
            }
        }
        return null;
    }
}
