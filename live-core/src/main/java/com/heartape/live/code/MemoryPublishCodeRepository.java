package com.heartape.live.code;

import com.heartape.live.exception.PermissionDeniedException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 将推流码在内存中管理
 */
public class MemoryPublishCodeRepository implements PublishCodeRepository {

    private final Map<String, String> codeToId = new HashMap<>();
    private final Map<String, String> idToCode = new HashMap<>();

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public MemoryPublishCodeRepository(PublishCode... publishCodes) {
        lock.writeLock().lock();
        for (PublishCode publishCode : publishCodes) {
            codeToId.put(publishCode.getCode(), publishCode.getId());
            idToCode.put(publishCode.getId(), publishCode.getCode());
        }
        lock.writeLock().unlock();
    }

    @Override
    public void insert(PublishCode publishCode) {
        Objects.requireNonNull(publishCode);
        String code = publishCode.getCode();
        String id = publishCode.getId();
        if (code == null || code.isBlank() || id == null || id.isBlank()){
            throw new IllegalArgumentException();
        }
        lock.writeLock().lock();
        codeToId.put(code, id);
        idToCode.put(id, code);
        lock.writeLock().unlock();
    }

    @Override
    public String select(String code) {
        if (code != null && !code.isBlank()){
            lock.readLock().lock();
            String id = codeToId.get(code);
            lock.readLock().unlock();
            if (id != null && !id.isBlank()){
                return id;
            }
        }
        throw new PermissionDeniedException("没有权限！");
    }

    @Override
    public void delete(String id) {
        lock.writeLock().lock();
        String code = idToCode.get(id);
        codeToId.remove(code);
        idToCode.remove(id);
        lock.writeLock().unlock();
    }
}
