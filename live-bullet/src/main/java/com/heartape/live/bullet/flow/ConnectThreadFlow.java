package com.heartape.live.bullet.flow;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * 通过连接池，实现推送数据
 */
public class ConnectThreadFlow extends ThreadFlow {

    /**
     * 推送堆积阈值
     */
    private static final int CACHE_THRESHOLD = 10;
    /**
     * 推送周期，用于推送数据较少的目标
     */
    private static final int PUSH_PERIOD = 100;

    public ConnectThreadFlow(BiConsumer<String, Object> callback) {
        super(callback);
    }

    @Override
    protected Runnable task() {
        return () -> {
            while (!isStopped()){
                if (isEmpty()){
                    idle();
                }

                for (String key : elements.keySet()) {
                    List<FlowElement> flowElements = elements.get(key);
                    if (!flowElements.isEmpty()) {
                        callback.accept(key, flowElements);
                        flowElements.clear();
                    } else {
                        elements.remove(key);
                    }
                }

                int time = PUSH_PERIOD;
                while (time >= 0 && !cache.isEmpty()){
                    FlowElement flowElement = cache.poll();
                    String destination = flowElement.getDestination();
                    if (!elements.containsKey(destination)){
                        LinkedList<FlowElement> list = new LinkedList<>();
                        list.add(flowElement);
                        elements.put(destination, list);
                    } else {
                        elements.get(destination).add(flowElement);
                    }
                    List<FlowElement> flowElements = elements.get(destination);
                    if (flowElements.size() >= CACHE_THRESHOLD){
                        callback.accept(destination, flowElements);
                        flowElements.clear();
                    }
                    time--;
                }
            }
        };
    }
}
