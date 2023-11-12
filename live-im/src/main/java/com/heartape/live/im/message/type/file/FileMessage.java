package com.heartape.live.im.message.type.file;

import com.heartape.live.im.message.base.BaseMessage;

import java.time.LocalDateTime;

/**
 * 文件
 * @see BaseMessage
 * @see File
 * @since 0.0.1
 * @author heartape
 */
public class FileMessage extends BaseMessage<File> {
    public FileMessage(String id, String uid, String purpose, String purposeType, String type, LocalDateTime timestamp, File content) {
        super(id, uid, purpose, purposeType, type, 0, timestamp, content);
    }
}
