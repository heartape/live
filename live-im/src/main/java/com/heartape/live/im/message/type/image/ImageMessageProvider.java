package com.heartape.live.im.message.type.image;

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
public class ImageMessageProvider extends AbstractBaseMessageProvider<ImageMessage> {

    public ImageMessageProvider(MessageConverter<ImageMessage> messageConverter,
                                FilterManager<ImageMessage> filterManager,
                                MessageRepository<ImageMessage> groupRepository,
                                MessageRepository<ImageMessage> userRepository) {
        this(MessageType.IMAGE, messageConverter, filterManager, groupRepository, userRepository);
    }

    public ImageMessageProvider(String type,
                                MessageConverter<ImageMessage> messageConverter,
                                FilterManager<ImageMessage> filterManager,
                                MessageRepository<ImageMessage> groupRepository,
                                MessageRepository<ImageMessage> userRepository) {
        super(type, messageConverter, filterManager, groupRepository, userRepository);
    }

}
