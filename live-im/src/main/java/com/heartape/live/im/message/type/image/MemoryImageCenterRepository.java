package com.heartape.live.im.message.type.image;

import com.heartape.live.im.message.MessageRepository;
import com.heartape.live.im.message.MessageType;
import com.heartape.live.im.util.Page;
import com.heartape.live.im.message.base.AbstractCenterBaseRepository;
import com.heartape.live.im.message.base.BaseMessage;
import com.heartape.live.im.message.center.CenterMessageRepository;
import com.heartape.live.im.util.IdentifierGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于中央仓库的实现
 * @since 0.0.1
 * @author heartape
 * @see MessageRepository
 */
public class MemoryImageCenterRepository extends AbstractCenterBaseRepository<ImageMessage> {

    private final CenterMessageRepository centerMessageRepository;

    public MemoryImageCenterRepository(IdentifierGenerator<Long> identifierGenerator,
                                       CenterMessageRepository centerMessageRepository) {
        super(identifierGenerator, centerMessageRepository);
        this.centerMessageRepository = centerMessageRepository;
    }

    @Override
    public ImageMessage findById(Long id, String uid) {
        BaseMessage baseMessage = this.centerMessageRepository.findById(id, uid);
        if (baseMessage != null){
            if (baseMessage instanceof ImageMessage imageMessage){
                return imageMessage;
            }
        }
        return null;
    }

    @Override
    public Page<ImageMessage> findByPurposeId(String uid, String purposeId, int page, int size) {
        return super.findByPurposeId(uid, purposeId, MessageType.IMAGE, page, size);
    }

    @Override
    public Page<ImageMessage> findByStartId(Long id, String uid, String purposeId, int page, int size) {
        return super.findByStartId(id, uid, purposeId, MessageType.IMAGE, page, size);
    }

    @Override
    public Page<ImageMessage> findRoamingByPurposeId(String uid, String purposeId, int page, int size) {
        return super.findRoamingByPurposeId(uid, purposeId, MessageType.IMAGE, page, size);
    }

    @Override
    public Page<ImageMessage> findRoamingByStartId(Long id, String uid, String purposeId, int page, int size) {
        return super.findRoamingByStartId(id, uid, purposeId, MessageType.IMAGE, page, size);
    }

    protected Page<ImageMessage> convert(Page<BaseMessage> baseMessagePage){
        if (baseMessagePage.getList().size() == 0){
            return new Page<>(baseMessagePage.getPage(), baseMessagePage.getSize(), baseMessagePage.getTotal(), new ArrayList<>());
        }

        List<ImageMessage> imageMessageList = baseMessagePage.getList()
                .stream()
                .map(baseMessage -> (ImageMessage)baseMessage)
                .toList();
        return new Page<>(baseMessagePage.getPage(), baseMessagePage.getSize(), baseMessagePage.getTotal(), imageMessageList);
    }

}
