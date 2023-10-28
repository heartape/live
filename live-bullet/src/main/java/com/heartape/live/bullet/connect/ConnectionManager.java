package com.heartape.live.bullet.connect;

/**
 * 连接管理
 */
public interface ConnectionManager {

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
     * 注册房间
     */
    void register(String roomId);

    /**
     * 注册连接
     */
    void register(Connection connection);

    /**
     * 已注册连接
     */
    boolean registered(String roomId, String uid);

    void push(String roomId, Object o);

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
    void logout(Connection connection);
}
