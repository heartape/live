package com.heartape.live.bullet.filter;

import com.heartape.live.bullet.repository.bullet.Bullet;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 黑名单过滤器
 */
public class MemoryBlackListFilter implements Filter<Bullet> {

    private final Set<String> blackList = new ConcurrentSkipListSet<>();

    public MemoryBlackListFilter(Set<String> blackSet) {
        this.blackList.addAll(blackSet);
    }

    @Override
    public boolean permit(Bullet bullet) {
        String uid = bullet.getUid();
        return uid != null && !blackList.contains(uid);
    }

}
