package com.heartape.live.im.message.type.greeting;

import com.heartape.live.im.message.*;
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
 */
public class MemoryGreetingCenterRepository extends AbstractCenterBaseRepository<GreetingMessage> {

    private final CenterMessageRepository centerMessageRepository;

    public MemoryGreetingCenterRepository(IdentifierGenerator<Long> identifierGenerator,
                                          CenterMessageRepository centerMessageRepository) {
        super(identifierGenerator, centerMessageRepository);
        this.centerMessageRepository = centerMessageRepository;
    }

    @Override
    public GreetingMessage findById(String id, String uid) {
        BaseMessage baseMessage = this.centerMessageRepository.findById(id, uid);
        if (baseMessage != null){
            if (baseMessage instanceof GreetingMessage greetingMessage){
                return greetingMessage;
            }
        }
        return null;
    }

    protected Page<GreetingMessage> convert(Page<BaseMessage> baseMessagePage){
        if (baseMessagePage.getList().size() == 0){
            return new Page<>(baseMessagePage.getPage(), baseMessagePage.getSize(), baseMessagePage.getTotal(), new ArrayList<>());
        }

        List<GreetingMessage> textMessageList = baseMessagePage.getList()
                .stream()
                .map(baseMessage -> (GreetingMessage)baseMessage)
                .toList();
        return new Page<>(baseMessagePage.getPage(), baseMessagePage.getSize(), baseMessagePage.getTotal(), textMessageList);
    }

    @Override
    public Page<GreetingMessage> findByPurposeId(String uid, String purposeId, int page, int size) {
        return super.findByPurposeId(uid, purposeId, MessageType.GREETING, page, size);
    }

    @Override
    public Page<GreetingMessage> findByStartId(String id, String uid, String purposeId, int page, int size) {
        return super.findByStartId(id, uid, purposeId, MessageType.GREETING, page, size);
    }

    @Override
    public Page<GreetingMessage> findRoamingByPurposeId(String uid, String purposeId, int page, int size) {
        return super.findRoamingByPurposeId(uid, purposeId, MessageType.GREETING, page, size);
    }

    @Override
    public Page<GreetingMessage> findRoamingByStartId(String id, String uid, String purposeId, int page, int size) {
        return super.findRoamingByStartId(id, uid, purposeId, MessageType.GREETING, page, size);
    }

}
