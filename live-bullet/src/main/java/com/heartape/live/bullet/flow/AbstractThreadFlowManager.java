package com.heartape.live.bullet.flow;

/**
 * 以 {@link  ThreadFlow}作为Flow的实现
 */
public abstract class AbstractThreadFlowManager extends AbstractFlowManager {

    public AbstractThreadFlowManager(int time, int maxFlowSizePow, int coreFlowSizePow) {
        super(time, maxFlowSizePow, coreFlowSizePow);
    }

    @Override
    protected Flow create(Runnable task) {
        return new ThreadFlow(task);
    }

    @Override
    protected long getId() {
        return Thread.currentThread().getId();
    }

    @Override
    protected void task() {
        int seat = getSeat();
        Flow flow = getFlow(seat);
        while (!flow.isStopped()){
            long frame = flow.getLastFrame();
            long end = System.currentTimeMillis();
            long timeTarget = end - (long) this.time;
            long start = Math.max(frame, timeTarget);
            flow.setLastFrame(end);
            if (this.doTaskCore(seat, getFlowSize(), start, end)){
                flow.idle(this.time);
            }
        }
    }

    /**
     * Flow核心任务
     * @param seat 当前流次序
     * @param flowSize 全部流的数量
     * @param start 开始
     * @param end 结束
     * @return Flow是否空闲
     */
    protected abstract boolean doTaskCore(int seat, int flowSize, long start, long end);
}
