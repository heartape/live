package com.heartape.live.bullet.flow;

/**
 * 流管理器,用以管理流的生命周期
 */
public interface FlowManager {

    /**
     * @param seat Flow所处的位置或次序
     * @return Flow
     */
    Flow getFlow(int seat);

    /**
     * 推送
     */
    void push(FlowElement element);

    /**
     * @return 流的数量
     */
    int getFlowSize();

    /**
     * 设置流的数量
     * @param flowSize 流的数量
     */
    void setFlowSize(int flowSize);

    /**
     * 优雅停机
     */
    void stop();
}
