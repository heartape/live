package com.heartape.live.im.prompt.apply;

import com.heartape.live.im.prompt.Prompt;
import lombok.AllArgsConstructor;

/**
 * 好友申请，加入群聊申请等
 * @since 0.0.1
 * @author heartape
 */
@AllArgsConstructor
public class ApplyPrompt implements Prompt {

    /**
     * id，因为作为通知，所以仅仅需要事件id，从管理用户相关信息的系统中拉取详细内容
     */
    private final String id;

}
