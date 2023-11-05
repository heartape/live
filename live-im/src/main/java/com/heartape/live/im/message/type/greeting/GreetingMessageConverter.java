package com.heartape.live.im.message.type.greeting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartape.live.im.context.MessageContext;
import com.heartape.live.im.message.base.BaseMessage;
import com.heartape.live.im.message.MessageConverter;

import java.time.LocalDateTime;

/**
 * 文本消息转换
 * @see MessageConverter
 * @since 0.0.1
 * @author heartape
 */
public class GreetingMessageConverter implements MessageConverter<GreetingMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public GreetingMessage convert(MessageContext messageContext) throws JsonProcessingException {
        JsonNode jsonNode = this.objectMapper.readTree(messageContext.getMessageStr()).get("content");
        Greeting greeting = this.objectMapper.treeToValue(jsonNode, Greeting.class);
        return new GreetingMessage(null, messageContext.getUid(), messageContext.getPurpose(), messageContext.getPurposeType(), messageContext.getMessageType(), LocalDateTime.now(), greeting);
    }
}
