package com.heartape.live.im.message.type.file;

import com.heartape.live.im.message.base.BaseMessage;
import com.heartape.live.im.message.base.ContentMessage;

/**
 * 文件
 * @see ContentMessage
 * @see File
 * @since 0.0.1
 * @author heartape
 */
public class FileMessage extends ContentMessage<File> {
    public FileMessage(BaseMessage baseMessage, File content) {
        super(baseMessage, content);
    }
}
