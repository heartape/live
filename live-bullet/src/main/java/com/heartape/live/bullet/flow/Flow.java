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
     * 推送
     */
    void push(FlowElement element);

    /**
     * 是否为空
     */
    boolean isEmpty();

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
     * 激活暂停状态的flow进行下一波流推送
     */
    void next();

    /**
     * 停止
     */
    void stop();

    /**
     * 是否已停止
     */
    boolean isStopped();

}
