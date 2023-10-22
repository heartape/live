package com.heartape.live.im.message.type.location;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartape.live.im.context.MessageContext;
import com.heartape.live.im.message.MessageConverter;
import com.heartape.live.im.message.base.BaseMessage;

import java.time.LocalDateTime;

/**
 * 文本消息转换
 * @see MessageConverter
 * @since 0.0.1
 * @author heartape
 */
public class LocationMessageConverter implements MessageConverter<LocationMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public LocationMessage convert(MessageContext messageContext) throws JsonProcessingException {
        JsonNode jsonNode = this.objectMapper.readTree(messageContext.getMessageStr()).get("content");
        Location location = this.objectMapper.treeToValue(jsonNode, Location.class);
        BaseMessage baseMessage = new BaseMessage(null, messageContext.getUid(), messageContext.getPurpose(), messageContext.getPurposeType(), messageContext.getMessageType(), LocalDateTime.now());
        return new LocationMessage(baseMessage, location);
    }
}
