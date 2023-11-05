package com.heartape.live.im.message.type.location;

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
public class MemoryLocationCenterRepository extends AbstractCenterBaseRepository<LocationMessage> {

    private final CenterMessageRepository centerMessageRepository;

    public MemoryLocationCenterRepository(IdentifierGenerator<Long> identifierGenerator,
                                          CenterMessageRepository centerMessageRepository) {
        super(identifierGenerator, centerMessageRepository);
        this.centerMessageRepository = centerMessageRepository;
    }

    @Override
    public LocationMessage findById(String id, String uid) {
        BaseMessage<?> baseMessage = this.centerMessageRepository.findById(id, uid);
        if (baseMessage != null){
            if (baseMessage instanceof LocationMessage locationMessage){
                return locationMessage;
            }
        }
        return null;
    }

    @Override
    public Page<LocationMessage> findByPurposeId(String uid, String purposeId, int page, int size) {
        return super.findByPurposeId(uid, purposeId, MessageType.LOCATION, page, size);
    }

    @Override
    public Page<LocationMessage> findByStartId(String id, String uid, String purposeId, int page, int size) {
        return super.findByStartId(id, uid, purposeId, MessageType.LOCATION, page, size);
    }

    @Override
    public Page<LocationMessage> findRoamingByPurposeId(String uid, String purposeId, int page, int size) {
        return super.findRoamingByPurposeId(uid, purposeId, MessageType.LOCATION, page, size);
    }

    @Override
    public Page<LocationMessage> findRoamingByStartId(String id, String uid, String purposeId, int page, int size) {
        return super.findRoamingByStartId(id, uid, purposeId, MessageType.LOCATION, page, size);
    }

    protected Page<LocationMessage> convert(Page<BaseMessage<?>> baseMessagePage){
        if (baseMessagePage.getList().size() == 0){
            return new Page<>(baseMessagePage.getPage(), baseMessagePage.getSize(), baseMessagePage.getTotal(), new ArrayList<>());
        }

        List<LocationMessage> locationMessageList = baseMessagePage.getList()
                .stream()
                .map(baseMessage -> (LocationMessage)baseMessage)
                .toList();
        return new Page<>(baseMessagePage.getPage(), baseMessagePage.getSize(), baseMessagePage.getTotal(), locationMessageList);
    }

}
