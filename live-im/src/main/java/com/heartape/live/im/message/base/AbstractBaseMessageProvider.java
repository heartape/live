package com.heartape.live.im.message.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.heartape.live.im.context.MessageContext;
import com.heartape.live.im.gateway.PurposeType;
import com.heartape.live.im.message.filter.FilterManager;
import com.heartape.live.im.message.Message;
import com.heartape.live.im.message.MessageConverter;
import com.heartape.live.im.message.MessageProvider;
import com.heartape.live.im.message.MessageRepository;
import com.heartape.live.im.message.MessageType;
import com.heartape.live.im.send.ErrorSend;
import com.heartape.live.im.send.MessageSend;
import com.heartape.live.im.send.Send;

import java.util.Objects;

/**
 * @see MessageProvider
 * @since 0.0.1
 * @author heartape
 */
public abstract class AbstractBaseMessageProvider<T extends Message> implements MessageProvider {

    /**
     * @see MessageType
     */
    private final String type;
    /**
     * @see MessageConverter
     */
    private final MessageConverter<T> messageConverter;

    private final FilterManager<T> filterManager;

    private final MessageRepository<T> groupRepository;

    private final MessageRepository<T> userRepository;

    public AbstractBaseMessageProvider(String type,
                                       MessageConverter<T> messageConverter,
                                       FilterManager<T> filterManager,
                                       MessageRepository<T> groupRepository,
                                       MessageRepository<T> userRepository) {
        this.type = type;
        this.messageConverter = messageConverter;
        this.filterManager = filterManager;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Send process(MessageContext messageContext) {
        T message;
        try {
            message = this.messageConverter.convert(messageContext);
        } catch (JsonProcessingException e) {
            return new ErrorSend( "MESSAGE PARSE ERROR");
        }
        if (message == null || !Objects.equals(message.getType(), this.type)){
            return new ErrorSend( "MESSAGE PARSE ERROR");
        }
        Send send = this.filterManager.doFilter(message);
        if (send != null){
            return send;
        }
        String purposeType = messageContext.getPurposeType();
        messageRepository(purposeType).save(message);
        return new MessageSend(message);
    }

    private MessageRepository<T> messageRepository(String purposeType){
        if (PurposeType.GROUP.equals(purposeType)){
            return this.groupRepository;
        } else if (PurposeType.PERSON.equals(purposeType)){
            return this.userRepository;
        }
        throw new IllegalArgumentException("purposeType not support");
    }
}
