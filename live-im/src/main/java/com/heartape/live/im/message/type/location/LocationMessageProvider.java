package com.heartape.live.im.message.type.location;

import com.heartape.live.im.message.MessageConverter;
import com.heartape.live.im.message.MessageRepository;
import com.heartape.live.im.message.MessageType;
import com.heartape.live.im.message.base.AbstractBaseMessageProvider;
import com.heartape.live.im.message.filter.FilterManager;

/**
 * @see AbstractBaseMessageProvider
 * @since 0.0.1
 * @author heartape
 */
public class LocationMessageProvider extends AbstractBaseMessageProvider<LocationMessage> {

    public LocationMessageProvider(MessageConverter<LocationMessage> messageConverter,
                                   FilterManager<LocationMessage> filterManager,
                                   MessageRepository<LocationMessage> groupRepository,
                                   MessageRepository<LocationMessage> userRepository) {
        this(MessageType.LOCATION, messageConverter, filterManager, groupRepository, userRepository);
    }

    public LocationMessageProvider(String type,
                                   MessageConverter<LocationMessage> messageConverter,
                                   FilterManager<LocationMessage> filterManager,
                                   MessageRepository<LocationMessage> groupRepository,
                                   MessageRepository<LocationMessage> userRepository) {
        super(type, messageConverter, filterManager, groupRepository, userRepository);
    }

}
