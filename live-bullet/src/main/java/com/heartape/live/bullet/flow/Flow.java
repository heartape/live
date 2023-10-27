package com.heartape.live.bullet.flow;

/**
 * 流，用于对模型简单而持续的数据进行推送
 */
public interface Flow {

    /**
     * @return id
     */
    long getId();

    /**
     * 开始流
     */
    void start();

    /**
     * @return 是否在运行
     */
    boolean isRunning();

    /**
     * 被停用的流不会销毁，会进入睡眠状态
     */
    void sleep();

    /**
     * @return 是否在睡眠状态
     */
    boolean isSleeping();

    /**
     * 唤醒被睡眠或停用的流
     */
    void activate();

    /**
     * 空闲flow会暂停一段时间
     * @param time 暂停时间，单位毫秒
     */
    void idle(int time);

    /**
     * 激活暂停状态的flow进行下一波流推送
     */
    void next();

    /**
     * 获取流的上一帧
     */
    long getLastFrame();

    /**
     * 设置流的上一帧
     */
    void setLastFrame(long frame);

    /**
     * 停止
     */
    void stop();

    /**
     * 是否已停止
     */
    boolean isStopped();

}
