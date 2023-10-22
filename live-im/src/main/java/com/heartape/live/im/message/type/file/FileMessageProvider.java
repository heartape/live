package com.heartape.live.im.message.type.file;

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
public class FileMessageProvider extends AbstractBaseMessageProvider<FileMessage> {

    public FileMessageProvider(MessageConverter<FileMessage> messageConverter,
                               FilterManager<FileMessage> filterManager,
                               MessageRepository<FileMessage> groupRepository,
                               MessageRepository<FileMessage> userRepository) {
        this(MessageType.FILE, messageConverter, filterManager, groupRepository, userRepository);
    }

    public FileMessageProvider(String type,
                               MessageConverter<FileMessage> messageConverter,
                               FilterManager<FileMessage> filterManager,
                               MessageRepository<FileMessage> groupRepository,
                               MessageRepository<FileMessage> userRepository) {
        super(type, messageConverter, filterManager, groupRepository, userRepository);
    }

}
