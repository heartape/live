package com.heartape.live.bullet.flow;

import lombok.extern.slf4j.Slf4j;

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

    /**
     * 核心流的数量
     */
    private final int coreFlowSize;

    /**
     * 当前流的数量，值为2的指数幂，为了分配flow时提高性能
     */
    private int flowSize;

    private final Flow[] flowArray;

    // AtomicReferenceArray<AtomicReferenceArray<FlowElement>> cacheArray = new AtomicReferenceArray<>(16);
    // CyclicBarrier

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

    /**
     * todo:建立一个可以缓存并批量推送的cache，考虑如何线程安全地将元素放入cache中，cache应该采用什么样的形式
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
        if (flowSizePow > maxFlowSize) {
            log.warn("The number of Flow({}) exceeds the maximum number !", flowSizePow);
        } else {
            reset(1 << flowSizePow);
        }
    }

    /**
     * 创建流
     * @return Flow
     */
    protected abstract Flow create();

    private void reset(int flowSize){
        synchronized (this.flowArray) {
            if (this.current < flowSize - 1){
                while (this.current < flowSize - 1) {
                    Flow flow = this.flowArray[this.current + 1];
                    if (flow == null){
                        flow = create();
                        this.flowArray[++this.current] = flow;
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
                    flow.stop();
                    this.flowArray[this.current--].sleep();
                }
            }
        }
    }

    @Override
    public synchronized void stop() {
        for (Flow flow : this.flowArray) {
            flow.stop();
        }
    }
}
