package com.heartape.live.bullet.connect;

import java.util.Map;

/**
 * 连接管理
 */
public interface ConnectionManager<T> {

    /**
     * 已注册连接数量
     */
    int count();

    /**
     * 已注册房间的数量
     */
    int roomCount();

    /**
     * 房间内已注册连接数量
     */
    int count(String roomId);

    /**
     * 注册连接
     */
    void register(Connection<T> connection);

    /**
     * 已注册连接
     */
    boolean registered(String roomId, String uid);

    /**
     * 通过uid获取连接
     */
    Connection<T> pick(String roomId, String uid);

    /**
     * 获取直播间的所有连接
     */
    Map<String, Connection<T>> pickRoom(String roomId);

    /**
     * 根据hash取key
     * @param seat 当前需要的位置
     * @param range 取模范围
     * @return 符合的key
     */
    Map<String, Map<String, Connection<T>>> pickRoom(int seat, int range);

    /**
     * 根据hash取seat
     */
    int seat(String roomId, int range);

    /**
     * 将连接升级
     * @param uid 用户id
     */
    void upgrade(String roomId, String uid);

    /**
     * 将连接降级
     * @param uid 用户id
     */
    void relegation(String roomId, String uid);

    /**
     * 通过uid获取连接，将连接真正地中断，随后将Connection从ConnectionManager中排除
     * @param uid 用户id
     */
    void logout(String roomId, String uid);

    /**
     * 将连接真正地中断
     * @param connection 连接
     */
    void logout(Connection<T> connection);
}
