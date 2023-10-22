package com.heartape.live.im.context;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 消息上下文
 * @since 0.0.1
 * @author heartape
 */
@Getter
@AllArgsConstructor
public class MessageContext implements Context {

    /**
     * 发起人id
     */
    private final String uid;

    /**
     * 目标id
     */
    private final String purpose;

    /**
     * 目标类型
     * @see com.heartape.live.im.gateway.PurposeType
     */
    private final String purposeType;

    /**
     * 消息类型
     * @see com.heartape.live.im.message.MessageType
     */
    @Setter
    private String messageType;

    /**
     * 消息json字符串
     */
    private final String messageStr;

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String uid;

        private String purpose;

        private String purposeType;

        private String messageType;

        private String messageStr;

        public Builder uid(String uid){
            this.uid = uid;
            return this;
        }

        public Builder purpose(String purpose){
            this.purpose = purpose;
            return this;
        }

        public Builder purposeType(String purposeType){
            this.purposeType = purposeType;
            return this;
        }

        public Builder messageType(String messageType){
            this.messageType = messageType;
            return this;
        }

        public Builder messageStr(String messageStr){
            this.messageStr = messageStr;
            return this;
        }

        public MessageContext build(){
            return new MessageContext(this.uid, this.purpose, this.purposeType, this.messageType, this.messageStr);
        }

    }

}
