package com.heartape.live.streaming.code;

/**
 * 提供对推流吗的鉴权功能
 */
public interface CodeAuthenticationProvider {

    /**
     * 执行鉴权
     * @param code 推流码
     * @param stream 流id
     * @return 是否允许
     */
    boolean authenticate(String code, String stream);

}
