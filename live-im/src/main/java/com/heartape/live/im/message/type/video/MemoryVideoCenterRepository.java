package com.heartape.live.im.message.type.video;

import com.heartape.live.im.message.MessageRepository;
import com.heartape.live.im.message.MessageType;
import com.heartape.live.im.message.base.AbstractCenterBaseRepository;
import com.heartape.live.im.message.base.BaseMessage;
import com.heartape.live.im.message.center.CenterMessageRepository;
import com.heartape.util.id.IdentifierGenerator;
import com.heartape.util.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于中央仓库的实现
 * @since 0.0.1
 * @author heartape
 * @see MessageRepository
 * @see AbstractCenterBaseRepository
 */
public class MemoryVideoCenterRepository extends AbstractCenterBaseRepository<VideoMessage> {

    private final CenterMessageRepository centerMessageRepository;

    public MemoryVideoCenterRepository(IdentifierGenerator<Long> identifierGenerator,
                                       CenterMessageRepository centerMessageRepository) {
        super(identifierGenerator, centerMessageRepository);
        this.centerMessageRepository = centerMessageRepository;
    }

    @Override
    public VideoMessage findById(Long id, String uid) {
        BaseMessage baseMessage = this.centerMessageRepository.findById(id, uid);
        if (baseMessage != null){
            if (baseMessage instanceof VideoMessage videoMessage){
                return videoMessage;
            }
        }
        return null;
    }

    @Override
    public Page<VideoMessage> findByPurposeId(String uid, String purposeId, int page, int size) {
        return super.findByPurposeId(uid, purposeId, MessageType.VIDEO, page, size);
    }

    @Override
    public Page<VideoMessage> findByStartId(Long id, String uid, String purposeId, int page, int size) {
        return super.findByStartId(id, uid, purposeId, MessageType.VIDEO, page, size);
    }

    @Override
    public Page<VideoMessage> findRoamingByPurposeId(String uid, String purposeId, int page, int size) {
        return super.findRoamingByPurposeId(uid, purposeId, MessageType.VIDEO, page, size);
    }

    @Override
    public Page<VideoMessage> findRoamingByStartId(Long id, String uid, String purposeId, int page, int size) {
        return super.findRoamingByStartId(id, uid, purposeId, MessageType.VIDEO, page, size);
    }

    protected Page<VideoMessage> convert(Page<BaseMessage> baseMessagePage){
        if (baseMessagePage.getList().size() == 0){
            return new Page<>(baseMessagePage.getPage(), baseMessagePage.getSize(), baseMessagePage.getTotal(), new ArrayList<>());
        }

        List<VideoMessage> videoMessageList = baseMessagePage.getList()
                .stream()
                .map(baseMessage -> (VideoMessage)baseMessage)
                .toList();
        return new Page<>(baseMessagePage.getPage(), baseMessagePage.getSize(), baseMessagePage.getTotal(), videoMessageList);
    }

}
