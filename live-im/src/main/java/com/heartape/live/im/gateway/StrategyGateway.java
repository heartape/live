package com.heartape.live.im.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartape.live.im.context.MessageContext;
import com.heartape.live.im.context.PromptContext;
import com.heartape.live.im.message.MessageProvider;
import com.heartape.live.im.prompt.PromptProvider;
import com.heartape.live.im.send.ErrorSend;
import com.heartape.live.im.send.Send;
import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * @see Gateway
 * @since 0.0.1
 * @author heartape
 */
@AllArgsConstructor
public class StrategyGateway implements Gateway {

    /**
     * MessageProvider Strategy
     */
    private final Map<String, MessageProvider> messageStrategy;

    /**
     * PromptProvider Strategy
     */
    private final Map<String, PromptProvider> promptStrategy;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Send message(MessageContext messageContext) {
        String type;
        try {
            JsonNode jsonNode = this.objectMapper.readTree(messageContext.getMessageStr());
            type = jsonNode.findValue("type").asText();
        } catch (JsonProcessingException e) {
            return new ErrorSend("TYPE MISSING");
        }
        if (!this.messageStrategy.containsKey(type)){
            return new ErrorSend("TYPE OUT OF RANGE");
        }
        messageContext.setMessageType(type);
        return this.messageStrategy.get(type).process(messageContext);
    }

    @Override
    public Send prompt(PromptContext promptContext) {
        String type = promptContext.getPromptType();
        if (!this.promptStrategy.containsKey(type)){
            throw new IllegalArgumentException("TYPE OUT OF RANGE");
        }
        return this.promptStrategy.get(type).process(promptContext);
    }
}
