package com.heartape.live.im.message.type.file;

import com.heartape.live.im.message.MessageType;
import com.heartape.live.im.message.base.AbstractCenterBaseRepository;
import com.heartape.live.im.message.base.BaseMessage;
import com.heartape.live.im.message.center.CenterMessageRepository;
import com.heartape.live.im.util.IdentifierGenerator;
import com.heartape.live.im.util.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于中央仓库的实现
 * @since 0.0.1
 * @author heartape
 * @see AbstractCenterBaseRepository
 */
public class MemoryFileCenterRepository extends AbstractCenterBaseRepository<FileMessage> {

    private final CenterMessageRepository centerMessageRepository;

    public MemoryFileCenterRepository(IdentifierGenerator<Long> identifierGenerator,
                                      CenterMessageRepository centerMessageRepository) {
        super(identifierGenerator, centerMessageRepository);
        this.centerMessageRepository = centerMessageRepository;
    }

    @Override
    public FileMessage findById(Long id, String uid) {
        BaseMessage baseMessage = this.centerMessageRepository.findById(id, uid);
        if (baseMessage != null){
            if (baseMessage instanceof FileMessage fileMessage){
                return fileMessage;
            }
        }
        return null;
    }

    @Override
    public Page<FileMessage> findByPurposeId(String uid, String purposeId, int page, int size) {
        return super.findByPurposeId(uid, purposeId, MessageType.FILE, page, size);
    }

    @Override
    public Page<FileMessage> findByStartId(Long id, String uid, String purposeId, int page, int size) {
        return super.findByStartId(id, uid, purposeId, MessageType.FILE, page, size);
    }

    @Override
    public Page<FileMessage> findRoamingByPurposeId(String uid, String purposeId, int page, int size) {
        return super.findRoamingByPurposeId(uid, purposeId, MessageType.FILE, page, size);
    }

    @Override
    public Page<FileMessage> findRoamingByStartId(Long id, String uid, String purposeId, int page, int size) {
        return super.findRoamingByStartId(id, uid, purposeId, MessageType.FILE, page, size);
    }

    protected Page<FileMessage> convert(Page<BaseMessage> baseMessagePage){
        if (baseMessagePage.getList().size() == 0){
            return new Page<>(baseMessagePage.getPage(), baseMessagePage.getSize(), baseMessagePage.getTotal(), new ArrayList<>());
        }

        List<FileMessage> textMessageList = baseMessagePage.getList()
                .stream()
                .map(baseMessage -> (FileMessage)baseMessage)
                .toList();
        return new Page<>(baseMessagePage.getPage(), baseMessagePage.getSize(), baseMessagePage.getTotal(), textMessageList);
    }

}
