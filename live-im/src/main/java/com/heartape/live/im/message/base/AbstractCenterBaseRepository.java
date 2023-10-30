package com.heartape.live.im.message.base;

import com.heartape.live.im.message.*;
import com.heartape.live.im.message.center.CenterMessageRepository;
import com.heartape.util.id.IdentifierGenerator;
import com.heartape.util.Page;

/**
 * 中央仓库实现
 * @since 0.0.1
 * @author heartape
 * @see MessageRepository
 */
public abstract class AbstractCenterBaseRepository<T extends Message> implements MessageRepository<T> {

    private final IdentifierGenerator<Long> identifierGenerator;

    private final CenterMessageRepository centerMessageRepository;

    protected AbstractCenterBaseRepository(IdentifierGenerator<Long> identifierGenerator, CenterMessageRepository centerMessageRepository) {
        this.identifierGenerator = identifierGenerator;
        this.centerMessageRepository = centerMessageRepository;
    }

    @Override
    public void save(T message) {
        if (message instanceof BaseMessage baseMessage){
            Long id = this.identifierGenerator.nextId();
            baseMessage.setId(id);
            this.centerMessageRepository.save(baseMessage);
        }
    }

    protected Page<T> findByPurposeId(String uid, String purposeId, String messageType, int page, int size) {
        Page<BaseMessage> baseMessagePage = this.centerMessageRepository.findByPurposeIdAndType(uid, purposeId, messageType, page, size);
        return convert(baseMessagePage);
    }

    protected Page<T> findByStartId(Long id, String uid, String purposeId, String messageType, int page, int size) {
        Page<BaseMessage> baseMessagePage = this.centerMessageRepository.findByStartIdAndType(id, uid, purposeId, messageType, page, size);
        return convert(baseMessagePage);
    }

    protected Page<T> findRoamingByPurposeId(String uid, String purposeId, String messageType, int page, int size) {
        Page<BaseMessage> baseMessagePage = this.centerMessageRepository.findRoamingByPurposeIdAndType(uid, purposeId, messageType, page, size);
        return convert(baseMessagePage);
    }

    protected Page<T> findRoamingByStartId(Long id, String uid, String purposeId, String messageType, int page, int size) {
        Page<BaseMessage> baseMessagePage = this.centerMessageRepository.findRoamingByStartIdAndType(id, uid, purposeId, messageType, page, size);
        return convert(baseMessagePage);
    }

    /**
     * 类型转换
     * @param baseMessagePage Page<BaseMessage>
     * @return 对应的具体类型
     */
    abstract protected Page<T> convert(Page<BaseMessage> baseMessagePage);


    @Override
    public void receipt(Long id, String uid) {

    }

    @Override
    public void recall(Long id, String uid) {

    }

    @Override
    public void remove(Long id, String uid) {

    }
}
