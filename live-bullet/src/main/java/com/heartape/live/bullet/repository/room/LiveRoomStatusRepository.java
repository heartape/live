package com.heartape.live.bullet.repository.room;

/**
 * 直播间管理，仅涉及直播间的状态管理
 */
public interface LiveRoomStatusRepository {

    /**
     * 注册直播间
     * @param id 直播间id
     */
    void insert(String id);

    /**
     * 注销直播间
     * @param id 直播间id
     */
    void delete(String id);

    /**
     * 封禁直播间
     * @param id 直播间id
     */
    void ban(String id);

    /**
     * 检查状态
     */
    boolean select(String id, Status status);

    enum Status {
        NORMAL,
        STOP,
        BAN
    }

}
