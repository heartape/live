package com.heartape.live.bullet.repository.room;

/**
 * 直播间的信息管理
 */
public interface LiveRoomRepository {

    /**
     * 创建直播间
     * @param liveRoom 直播间
     */
    void insert(LiveRoom liveRoom);

    /**
     * 通过id获取直播间的信息
     * @return 直播间的信息
     */
    LiveRoom select(String id);

    /**
     * 删除直播间
     */
    void delete(String id);

}
