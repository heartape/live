package com.heartape.live.im.context;

import com.heartape.live.im.prompt.Prompt;
import com.heartape.live.im.prompt.PromptType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统消息上下文
 * @since 0.0.1
 * @author heartape
 */
@Getter
@AllArgsConstructor
public class PromptContext implements Context {

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
     * @see PromptType
     */
    @Setter
    private String promptType;

    /**
     * 系统提示消息
     */
    private final Prompt prompt;

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String purpose;
        private String purposeType;
        private String tipType;
        private Prompt prompt;

        public Builder purpose(String purpose){
            this.purpose = purpose;
            return this;
        }

        public Builder purposeType(String purposeType){
            this.purposeType = purposeType;
            return this;
        }

        public Builder tipType(String tipType){
            this.tipType = tipType;
            return this;
        }

        public Builder prompt(Prompt prompt){
            this.prompt = prompt;
            return this;
        }

        public PromptContext build(){
            return new PromptContext(purpose, purposeType, tipType, prompt);
        }

    }

}
