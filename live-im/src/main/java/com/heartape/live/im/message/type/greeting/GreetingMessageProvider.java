package com.heartape.live.im.message.type.greeting;

import com.heartape.live.im.message.filter.FilterManager;
import com.heartape.live.im.message.MessageConverter;
import com.heartape.live.im.message.MessageRepository;
import com.heartape.live.im.message.MessageType;
import com.heartape.live.im.message.base.AbstractBaseMessageProvider;

/**
 * @see AbstractBaseMessageProvider
 * @since 0.0.1
 * @author heartape
 */
public class GreetingMessageProvider extends AbstractBaseMessageProvider<GreetingMessage> {

    public GreetingMessageProvider(MessageConverter<GreetingMessage> messageConverter,
                                   FilterManager<GreetingMessage> filterManager,
                                   MessageRepository<GreetingMessage> groupRepository,
                                   MessageRepository<GreetingMessage> userRepository) {
        this(MessageType.GREETING, messageConverter, filterManager, groupRepository, userRepository);
    }

    public GreetingMessageProvider(String type,
                                   MessageConverter<GreetingMessage> messageConverter,
                                   FilterManager<GreetingMessage> filterManager,
                                   MessageRepository<GreetingMessage> groupRepository,
                                   MessageRepository<GreetingMessage> userRepository) {
        super(type, messageConverter, filterManager, groupRepository, userRepository);
    }

}
