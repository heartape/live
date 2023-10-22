package com.heartape.live.im.message.center;

import com.heartape.live.im.gateway.PurposeType;
import com.heartape.live.im.message.Message;
import com.heartape.live.im.util.Page;
import com.heartape.live.im.message.base.BaseMessage;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 私聊存储内存实现，仅用于开发测试
 * @see CenterMessageRepository
 * @since 0.0.1
 * @author heartape
 */
@SuppressWarnings("DuplicatedCode")
public class MemoryUserCenterMessageRepository implements CenterMessageRepository {

    /**
     * 消息存储到内存
     */
    private final Map<Long, BaseMessage> map = new ConcurrentHashMap<>();

    @Override
    public String purposeType() {
        return PurposeType.USER;
    }

    @Override
    public void save(BaseMessage message) {
        this.map.put(message.getId(), message);
    }

    @Override
    public BaseMessage findById(Long id, String uid) {
        return map.get(id);
    }

    @Override
    public Page<BaseMessage> findByPurposeId(String uid, String purposeId, int page, int size) {
        long total = map.values()
                .stream()
                .filter(message -> Objects.equals(message.getPurpose(), purposeId) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), uid) ||
                                Objects.equals(message.getPurpose(), uid) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), purposeId))
                .count();

        List<BaseMessage> list = map.values()
                .stream()
                .filter(message -> Objects.equals(message.getPurpose(), purposeId) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), uid) ||
                        Objects.equals(message.getPurpose(), uid) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), purposeId))
                .sorted(Comparator.comparing(Message::getId))
                .skip((long) (page - 1) * size)
                .limit(size)
                .toList();

        return new Page<>(page, size, total, list);
    }

    @Override
    public Page<BaseMessage> findByStartId(Long id, String uid, String purposeId, int page, int size) {
        long total = map.values()
                .stream()
                .filter(message -> Objects.equals(message.getPurpose(), purposeId) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), uid) ||
                        Objects.equals(message.getPurpose(), uid) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), purposeId))
                .sorted(Comparator.comparing(Message::getId))
                .dropWhile(message -> message.getId() >= id)
                .count();

        List<BaseMessage> list = map.values()
                .stream()
                .filter(message -> Objects.equals(message.getPurpose(), purposeId) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), uid) ||
                        Objects.equals(message.getPurpose(), uid) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), purposeId))
                .sorted(Comparator.comparing(Message::getId))
                .dropWhile(message -> message.getId() >= id)
                .skip((long) (page - 1) * size)
                .limit(size)
                .toList();

        return new Page<>(page, size, total, list);
    }

    @Override
    public Page<BaseMessage> findRoamingByPurposeId(String uid, String purposeId, int page, int size) {
        long total = map.values()
                .stream()
                .filter(message -> Objects.equals(message.getPurpose(), purposeId) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), uid))
                .count();

        List<BaseMessage> list = map.values()
                .stream()
                .filter(message -> Objects.equals(message.getPurpose(), purposeId) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), uid))
                .sorted(Comparator.comparing(Message::getId))
                .skip((long) (page - 1) * size)
                .limit(size)
                .toList();

        return new Page<>(page, size, total, list);
    }

    @Override
    public Page<BaseMessage> findRoamingByStartId(Long id, String uid, String purposeId, int page, int size) {
        long total = map.values()
                .stream()
                .filter(message -> Objects.equals(message.getPurpose(), purposeId) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), uid))
                .sorted(Comparator.comparing(Message::getId))
                .dropWhile(message -> message.getId() >= id)
                .count();

        List<BaseMessage> list = map.values()
                .stream()
                .filter(message -> Objects.equals(message.getPurpose(), purposeId) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), uid))
                .sorted(Comparator.comparing(Message::getId))
                .dropWhile(message -> message.getId() >= id)
                .skip((long) (page - 1) * size)
                .limit(size)
                .toList();

        return new Page<>(page, size, total, list);
    }

    @Override
    public void receipt(Long id, String uid) {

    }

    @Override
    public void recall(Long id, String uid) {

    }

    @Override
    public void remove(Long id, String uid) {

    }

    @Override
    public Page<BaseMessage> findByPurposeIdAndType(String uid, String purposeId, String messageType, int page, int size) {
        long total = map.values()
                .stream()
                .filter(baseMessage -> Objects.equals(baseMessage.getType(), messageType))
                .filter(message -> Objects.equals(message.getPurpose(), purposeId) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), uid) ||
                        Objects.equals(message.getPurpose(), uid) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), purposeId))
                .count();

        List<BaseMessage> list = map.values()
                .stream()
                .filter(baseMessage -> Objects.equals(baseMessage.getType(), messageType))
                .filter(message -> Objects.equals(message.getPurpose(), purposeId) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), uid) ||
                        Objects.equals(message.getPurpose(), uid) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), purposeId))
                .sorted(Comparator.comparing(Message::getId))
                .skip((long) (page - 1) * size)
                .limit(size)
                .toList();

        return new Page<>(page, size, total, list);
    }

    @Override
    public Page<BaseMessage> findByStartIdAndType(Long id, String uid, String purposeId, String messageType, int page, int size) {
        long total = map.values()
                .stream()
                .filter(baseMessage -> Objects.equals(baseMessage.getType(), messageType))
                .filter(message -> Objects.equals(message.getPurpose(), purposeId) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), uid) ||
                        Objects.equals(message.getPurpose(), uid) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), purposeId))
                .sorted(Comparator.comparing(Message::getId))
                .dropWhile(message -> message.getId() >= id)
                .count();

        List<BaseMessage> list = map.values()
                .stream()
                .filter(baseMessage -> Objects.equals(baseMessage.getType(), messageType))
                .filter(message -> Objects.equals(message.getPurpose(), purposeId) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), uid) ||
                        Objects.equals(message.getPurpose(), uid) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), purposeId))
                .sorted(Comparator.comparing(Message::getId))
                .dropWhile(message -> message.getId() >= id)
                .skip((long) (page - 1) * size)
                .limit(size)
                .toList();

        return new Page<>(page, size, total, list);
    }

    @Override
    public Page<BaseMessage> findRoamingByPurposeIdAndType(String uid, String purposeId, String messageType, int page, int size) {
        long total = map.values()
                .stream()
                .filter(baseMessage -> Objects.equals(baseMessage.getType(), messageType))
                .filter(message -> Objects.equals(message.getPurpose(), purposeId) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), uid))
                .count();

        List<BaseMessage> list = map.values()
                .stream()
                .filter(baseMessage -> Objects.equals(baseMessage.getType(), messageType))
                .filter(message -> Objects.equals(message.getPurpose(), purposeId) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), uid))
                .sorted(Comparator.comparing(Message::getId))
                .skip((long) (page - 1) * size)
                .limit(size)
                .toList();

        return new Page<>(page, size, total, list);
    }

    @Override
    public Page<BaseMessage> findRoamingByStartIdAndType(Long id, String uid, String purposeId, String messageType, int page, int size) {
        long total = map.values()
                .stream()
                .filter(baseMessage -> Objects.equals(baseMessage.getType(), messageType))
                .filter(message -> Objects.equals(message.getPurpose(), purposeId) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), uid))
                .sorted(Comparator.comparing(Message::getId))
                .dropWhile(message -> message.getId() >= id)
                .count();

        List<BaseMessage> list = map.values()
                .stream()
                .filter(baseMessage -> Objects.equals(baseMessage.getType(), messageType))
                .filter(message -> Objects.equals(message.getPurpose(), purposeId) && purposeType().equals(message.getPurposeType()) && Objects.equals(message.getUid(), uid))
                .sorted(Comparator.comparing(Message::getId))
                .dropWhile(message -> message.getId() >= id)
                .skip((long) (page - 1) * size)
                .limit(size)
                .toList();

        return new Page<>(page, size, total, list);
    }
}
