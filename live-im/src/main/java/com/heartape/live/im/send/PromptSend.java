package com.heartape.live.im.send;

import com.heartape.live.im.prompt.Prompt;
import lombok.Getter;
import lombok.Setter;

/**
 * 服务端消息
 * @since 0.0.1
 * @author heartape
 */
@Getter
public class PromptSend implements Send {

    private final String type;

    /**
     * 目标
     */
    private final String purpose;

    /**
     * 目标类型
     * @see com.heartape.live.im.gateway.PurposeType
     */
    private final String purposeType;

    /**
     * 系统提示类型
     * @see com.heartape.live.im.prompt.PromptType
     */
    @Setter
    private String promptType;

    private final Prompt prompt;

    public PromptSend(String purpose, String purposeType, String promptType, Prompt prompt) {
        this(SendType.PROMPT, purpose, purposeType, promptType,prompt);
    }

    public PromptSend(String type, String purpose, String purposeType, String promptType, Prompt prompt) {
        this.type = type;
        this.purpose = purpose;
        this.purposeType = purposeType;
        this.promptType = promptType;
        this.prompt = prompt;
    }
}
