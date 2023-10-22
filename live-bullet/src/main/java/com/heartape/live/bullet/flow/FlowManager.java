package com.heartape.live.bullet.flow;

/**
 * 流管理器推拉结合:
 * <ul>
 *     <li>长连接推送
 *     <li>持久化
 *     <li>长连接不稳定会降级为短链接，主动拉流
 * </ul>
 */
public interface FlowManager {

    /**
     * @param seat Flow所处的位置或次序
     * @return Flow
     */
    Flow getFlow(int seat);

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
     * 停止全部
     */
    void stop();
}
