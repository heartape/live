package com.heartape.live.streaming.http;

import java.util.Map;

public interface ResponseConverter {

    /**
     * 获取所有的参数，用于序列化
     * @return 所有参数的键值对
     */
    Map<String, Object> parameters();
}
