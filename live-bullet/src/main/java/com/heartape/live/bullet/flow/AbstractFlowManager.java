package com.heartape.live.bullet.flow;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 仅负责flow管理。
 */
@Slf4j
public abstract class AbstractFlowManager implements FlowManager {

    /**
     * 推送间隔,单位毫秒
     */
    protected final int time;

    /**
     * 最大流的数量
     */
    private final int maxFlowSize;

    private final int coreFlowSize;

    /**
     * 当前流的数量，值为2的指数幂，为了分配flow时提高性能
     */
    private int flowSize;

    private final Flow[] flowArray;

    private final Map<Long, Integer> flowSeatMap = new HashMap<>();

    private int current = -1;

    public AbstractFlowManager(int time, int maxFlowSizePow, int coreFlowSizePow) {
        this.time = time;
        this.maxFlowSize = 1 << maxFlowSizePow;
        this.coreFlowSize = 1 << coreFlowSizePow;
        this.flowArray = new Flow[this.maxFlowSize];
    }

    @Override
    public Flow getFlow(int seat) {
        return flowArray[seat];
    }

    @Override
    public int getFlowSize(){
        return this.flowSize;
    }

    @Override
    public void setFlowSize(int flowSizePow){
        if (flowSizePow > maxFlowSize) {
            log.warn("The number of Flow({}) exceeds the maximum number !", flowSizePow);
        } else {
            reset(1 << flowSizePow);
        }
    }

    /**
     * 创建Flow
     * @param task 任务
     * @return Flow
     */
    protected abstract Flow create(Runnable task);

    private void reset(int flowSize){
        synchronized (this.flowArray) {
            if (this.current < flowSize - 1){
                while (this.current < flowSize - 1) {
                    Flow flow = this.flowArray[this.current + 1];
                    if (flow == null){
                        flow = create(this::task);
                        this.flowArray[++this.current] = flow;
                        long id = flow.getId();
                        this.flowSeatMap.put(id, this.current);
                        flow.start();
                    } else if (flow.isSleeping()){
                        flow.activate();
                    }
                }
                // 等Flow全部创建完成再修改
                this.flowSize = flowSize;
            } else {
                // 先修改再睡眠Flow
                this.flowSize = flowSize;
                while (this.current >= flowSize) {
                    Flow flow = this.flowArray[this.current];
                    long id = flow.getId();
                    flow.stop();
                    this.flowSeatMap.remove(id);
                    this.flowArray[this.current--].sleep();
                }
            }
        }
    }

    /**
     * flow任务
     */
    protected abstract void task();

    /**
     * @return 当前Flow id
     */
    protected abstract long getId();

    /**
     * @return 当前Flow次序或位置
     */
    protected int getSeat(){
        return this.flowSeatMap.get(getId());
    }

    @Override
    public void stop() {
        reset(0);
    }
}
