package com.heartape.live.im.message.type.sound;

import com.heartape.live.im.message.MessageRepository;
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
 * @see MessageRepository
 */
public class MemorySoundCenterRepository extends AbstractCenterBaseRepository<SoundMessage> {

    private final CenterMessageRepository centerMessageRepository;

    public MemorySoundCenterRepository(IdentifierGenerator<Long> identifierGenerator,
                                       CenterMessageRepository centerMessageRepository) {
        super(identifierGenerator, centerMessageRepository);
        this.centerMessageRepository = centerMessageRepository;
    }

    @Override
    public SoundMessage findById(Long id, String uid) {
        BaseMessage baseMessage = this.centerMessageRepository.findById(id, uid);
        if (baseMessage != null){
            if (baseMessage instanceof SoundMessage soundMessage){
                return soundMessage;
            }
        }
        return null;
    }

    @Override
    public Page<SoundMessage> findByPurposeId(String uid, String purposeId, int page, int size) {
        return super.findByPurposeId(uid, purposeId, MessageType.SOUND, page, size);
    }

    @Override
    public Page<SoundMessage> findByStartId(Long id, String uid, String purposeId, int page, int size) {
        return super.findByStartId(id, uid, purposeId, MessageType.SOUND, page, size);
    }

    @Override
    public Page<SoundMessage> findRoamingByPurposeId(String uid, String purposeId, int page, int size) {
        return super.findRoamingByPurposeId(uid, purposeId, MessageType.SOUND, page, size);
    }

    @Override
    public Page<SoundMessage> findRoamingByStartId(Long id, String uid, String purposeId, int page, int size) {
        return super.findRoamingByStartId(id, uid, purposeId, MessageType.SOUND, page, size);
    }

    protected Page<SoundMessage> convert(Page<BaseMessage> baseMessagePage){
        if (baseMessagePage.getList().size() == 0){
            return new Page<>(baseMessagePage.getPage(), baseMessagePage.getSize(), baseMessagePage.getTotal(), new ArrayList<>());
        }

        List<SoundMessage> soundMessageList = baseMessagePage.getList()
                .stream()
                .map(baseMessage -> (SoundMessage)baseMessage)
                .toList();
        return new Page<>(baseMessagePage.getPage(), baseMessagePage.getSize(), baseMessagePage.getTotal(), soundMessageList);
    }

}
