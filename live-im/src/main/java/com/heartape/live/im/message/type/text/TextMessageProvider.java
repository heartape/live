package com.heartape.live.im.message.type.text;

import com.heartape.live.im.message.filter.FilterManager;
import com.heartape.live.im.message.*;
import com.heartape.live.im.message.base.AbstractBaseMessageProvider;

/**
 * @see MessageProvider
 * @since 0.0.1
 * @author heartape
 */
public class TextMessageProvider extends AbstractBaseMessageProvider<TextMessage> {

    public TextMessageProvider(MessageConverter<TextMessage> messageConverter,
                               FilterManager<TextMessage> filterManager,
                               MessageRepository<TextMessage> groupRepository,
                               MessageRepository<TextMessage> userRepository) {
        this(MessageType.TEXT, messageConverter, filterManager, groupRepository, userRepository);
    }

    public TextMessageProvider(String type,
                               MessageConverter<TextMessage> messageConverter,
                               FilterManager<TextMessage> filterManager,
                               MessageRepository<TextMessage> groupRepository,
                               MessageRepository<TextMessage> userRepository) {
        super(type, messageConverter, filterManager, groupRepository, userRepository);
    }
}
