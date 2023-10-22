package com.heartape.live.im.message.type.image;

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
public class ImageMessageConverter implements MessageConverter<ImageMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ImageMessage convert(MessageContext messageContext) throws JsonProcessingException {
        JsonNode jsonNode = this.objectMapper.readTree(messageContext.getMessageStr()).get("content");
        Image image = this.objectMapper.treeToValue(jsonNode, Image.class);
        BaseMessage baseMessage = new BaseMessage(null, messageContext.getUid(), messageContext.getPurpose(), messageContext.getPurposeType(), messageContext.getMessageType(), LocalDateTime.now());
        return new ImageMessage(baseMessage, image);
    }
}
