package com.heartape.live.im.message.type.video;

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
public class VideoMessageProvider extends AbstractBaseMessageProvider<VideoMessage> {

    public VideoMessageProvider(MessageConverter<VideoMessage> messageConverter,
                                FilterManager<VideoMessage> filterManager,
                                MessageRepository<VideoMessage> groupRepository,
                                MessageRepository<VideoMessage> userRepository) {
        this(MessageType.VIDEO, messageConverter, filterManager, groupRepository, userRepository);
    }

    public VideoMessageProvider(String type,
                                MessageConverter<VideoMessage> messageConverter,
                                FilterManager<VideoMessage> filterManager,
                                MessageRepository<VideoMessage> groupRepository,
                                MessageRepository<VideoMessage> userRepository) {
        super(type, messageConverter, filterManager, groupRepository, userRepository);
    }

}
