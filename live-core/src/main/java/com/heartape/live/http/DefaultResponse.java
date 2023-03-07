package com.heartape.live.http;

import lombok.Getter;
import lombok.Setter;

/**
 * 通用请求响应
 */
@Getter
@Setter
public class DefaultResponse<T> {

    private Integer code;
    private String message;
    private T date;



}
