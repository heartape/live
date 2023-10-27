package com.heartape.live.bullet.flow;

import com.heartape.live.bullet.exception.FlowStatusException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * 以线程作为流的载体
 * @see Flow
 */
@Slf4j
@AllArgsConstructor
public class ThreadFlow implements Flow {

    private final Thread thread;

    private volatile long frame;

    private volatile int status = INIT;

    private final static int INIT = 0;

    private final static int RUN = 1;

    /** 暂停 */
    private final static int IDLE = 2;

    private final static int SLEEP = 3;

    private final static int STOP = 4;

    public ThreadFlow(Runnable task) {
        this.thread = new Thread(task);
        this.frame = System.currentTimeMillis();
    }

    @Override
    public long getId() {
        return thread.getId();
    }

    public void start() {
        this.thread.start();
        this.status = RUN;
        log.debug("Thread:{} start", this.thread.getId());
    }

    @Override
    public boolean isRunning() {
        return this.status == RUN;
    }

    @Override
    public void sleep() {
        if (this.status != RUN && this.status != SLEEP){
            throw new FlowStatusException("flow status error, can not sleep");
        }
        this.status = SLEEP;
        log.debug("Thread:{} sleep", this.thread.getId());
    }

    @Override
    public boolean isSleeping() {
        return this.status == SLEEP;
    }

    @Override
    public void activate() {
        this.status = RUN;
        log.debug("Thread:{} activate", this.thread.getId());
    }

    /**
     * 该方法仅可以在当前流所在的线程中调用
     */
    @Override
    public void idle(int time) {
        this.status = IDLE;
        LockSupport.parkNanos((long) time * 1000 * 1000 * 5);
        log.debug("Thread:{} idle", this.thread.getId());
    }

    @Override
    public void next() {
        LockSupport.unpark(this.thread);
        log.debug("Thread:{} next", this.thread.getId());
    }

    @Override
    public long getLastFrame() {
        return this.frame;
    }

    @Override
    public void setLastFrame(long frame) {
        this.frame = frame;
        log.debug("Thread:{} set frame", this.thread.getId());
    }

    public void stop() {
        this.status = STOP;
    }

    public boolean isStopped() {
        return this.status == STOP;
    }
}
