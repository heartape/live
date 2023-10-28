package com.heartape.live.bullet.flow;

import com.heartape.live.bullet.exception.FlowStatusException;
import lombok.extern.slf4j.Slf4j;

/**
 * 仅负责flow管理。
 */
@Slf4j
public abstract class AbstractFlowManager implements FlowManager {

    /**
     * 最大流的数量
     */
    private final int maxFlowSize;

    /**
     * 核心流的数量
     */
    private final int coreFlowSize;

    /**
     * 当前流的数量，值为2的指数幂，为了分配flow时提高性能
     */
    private int flowSize;

    private final Flow[] flowArray;

    // CyclicBarrier

    public AbstractFlowManager(int maxFlowSizePow, int coreFlowSizePow) {
        this.maxFlowSize = 1 << maxFlowSizePow;
        this.coreFlowSize = 1 << coreFlowSizePow;
        this.flowArray = new Flow[this.maxFlowSize];
    }

    @Override
    public Flow getFlow(int seat) {
        return flowArray[seat];
    }

    /**
     * @param element FlowElement
     */
    @Override
    public void push(FlowElement element) {
        String destination = element.getDestination();
        int seat = seat(destination);
        Flow flow = flowArray[seat];
        flow.push(element);
    }

    protected int seat(String destination) {
        return destination.hashCode() & getFlowSize() - 1;
    }

    @Override
    public int getFlowSize(){
        return this.flowSize;
    }

    @Override
    public void setFlowSize(int flowSizePow){
        int flowSize = 1 << flowSizePow;
        if (flowSize > maxFlowSize || flowSize < coreFlowSize) {
            log.warn("The number of Flow({}) is out of range !", flowSize);
        } else {
            reset(flowSize);
        }
    }

    /**
     * 创建流
     * @return Flow
     */
    protected abstract Flow create();

    private void reset(int flowSize){
        synchronized (this.flowArray) {
            if (this.flowSize < flowSize){
                for (int i = 0; i < flowSize - this.flowSize; i++) {
                    Flow flow = this.flowArray[this.flowSize + i];
                    if (flow == null){
                        flow = create();
                        this.flowArray[this.flowSize + i] = flow;
                        flow.start();
                    } else {
                        if (!flow.isSleeping()){
                            throw new FlowStatusException("flow status error, can not activate");
                        }
                        flow.activate();
                    }
                }
                // 等Flow全部创建完成再修改
                this.flowSize = flowSize;
            } else {
                int count = this.flowSize - flowSize;
                // 先修改再睡眠Flow
                this.flowSize = flowSize;
                for (int i = 0; i < count; i++) {
                    this.flowArray[this.flowSize + i].sleep();
                }
            }
        }
    }

    @Override
    public void stop() {
        synchronized (this.flowArray) {
            for (Flow flow : this.flowArray) {
                flow.stop();
            }
        }
    }
}
