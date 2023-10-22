package com.heartape.live.im.config;

import com.heartape.live.im.message.filter.FilterManager;
import com.heartape.live.im.message.Message;
import com.heartape.live.im.message.MessageConverter;
import com.heartape.live.im.message.MessageRepository;
import lombok.Getter;

/**
 * Provider配置
 * @param <T> Message
 */
@Getter
public abstract class AbstractProviderConfigurer<T extends Message> {

    private MessageConverter<T> messageConverter;

    private FilterManager<T> filterManager;

    private MessageRepository<T> groupRepository;

    private MessageRepository<T> userRepository;

    public AbstractProviderConfigurer<T> converter(MessageConverter<T> messageConverter){
        this.messageConverter = messageConverter;
        return this;
    }

    public AbstractProviderConfigurer<T> filterManager(FilterManager<T> filterManager){
        this.filterManager = filterManager;
        return this;
    }

    public AbstractProviderConfigurer<T> group(MessageRepository<T> messageRepository){
        this.groupRepository = messageRepository;
        return this;
    }

    public AbstractProviderConfigurer<T> user(MessageRepository<T> messageRepository){
        this.userRepository = messageRepository;
        return this;
    }

}
