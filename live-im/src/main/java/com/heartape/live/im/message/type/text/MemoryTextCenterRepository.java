package com.heartape.live.im.message.type.text;

import com.heartape.live.im.message.MessageType;
import com.heartape.live.im.message.base.AbstractCenterBaseRepository;
import com.heartape.live.im.message.base.BaseMessage;
import com.heartape.live.im.message.center.CenterMessageRepository;
import com.heartape.util.id.IdentifierGenerator;
import com.heartape.util.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 内存实现
 * @since 0.0.1
 * @author heartape
 * @see AbstractCenterBaseRepository
 */
public class MemoryTextCenterRepository extends AbstractCenterBaseRepository<TextMessage> {

    private final CenterMessageRepository centerMessageRepository;

    public MemoryTextCenterRepository(IdentifierGenerator<Long> identifierGenerator,
                                      CenterMessageRepository centerMessageRepository) {
        super(identifierGenerator, centerMessageRepository);
        this.centerMessageRepository = centerMessageRepository;
    }

    @Override
    protected Page<TextMessage> convert(Page<BaseMessage<?>> baseMessagePage){
        if (baseMessagePage.getList().size() == 0){
            return new Page<>(baseMessagePage.getPage(), baseMessagePage.getSize(), baseMessagePage.getTotal(), new ArrayList<>());
        }

        List<TextMessage> textMessageList = baseMessagePage.getList()
                .stream()
                .map(baseMessage -> (TextMessage)baseMessage)
                .toList();
        return new Page<>(baseMessagePage.getPage(), baseMessagePage.getSize(), baseMessagePage.getTotal(), textMessageList);
    }

    @Override
    public TextMessage findById(String id, String uid) {
        BaseMessage<?> baseMessage = this.centerMessageRepository.findById(id, uid);
        if (baseMessage != null){
            if (baseMessage instanceof TextMessage textMessage){
                return textMessage;
            }
        }
        return null;
    }

    @Override
    public Page<TextMessage> findByPurposeId(String uid, String purposeId, int page, int size) {
        return super.findByPurposeId(uid, purposeId, MessageType.TEXT, page, size);
    }

    @Override
    public Page<TextMessage> findByStartId(String id, String uid, String purposeId, int page, int size) {
        return super.findByStartId(id, uid, purposeId, MessageType.TEXT, page, size);
    }

    @Override
    public Page<TextMessage> findRoamingByPurposeId(String uid, String purposeId, int page, int size) {
        return super.findRoamingByPurposeId(uid, purposeId, MessageType.TEXT, page, size);
    }

    @Override
    public Page<TextMessage> findRoamingByStartId(String id, String uid, String purposeId, int page, int size) {
        return super.findRoamingByStartId(id, uid, purposeId, MessageType.TEXT, page, size);
    }
}
