package com.heartape.live.streaming.http;

import lombok.Getter;
import lombok.Setter;

/**
 * 通用请求响应
 */
@Getter
@Setter
public class DefaultResponse {

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 状态信息
     */
    private String message;
    /**
     * 数据
     */
    private Object date;

}
