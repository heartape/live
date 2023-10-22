package com.heartape.live.im.message.type.sound;

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
public class SoundMessageProvider extends AbstractBaseMessageProvider<SoundMessage> {

    public SoundMessageProvider(MessageConverter<SoundMessage> messageConverter,
                                FilterManager<SoundMessage> filterManager,
                                MessageRepository<SoundMessage> groupRepository,
                                MessageRepository<SoundMessage> userRepository) {
        this(MessageType.SOUND, messageConverter, filterManager, groupRepository, userRepository);
    }

    public SoundMessageProvider(String type,
                                MessageConverter<SoundMessage> messageConverter,
                                FilterManager<SoundMessage> filterManager,
                                MessageRepository<SoundMessage> groupRepository,
                                MessageRepository<SoundMessage> userRepository) {
        super(type, messageConverter, filterManager, groupRepository, userRepository);
    }

}
