package com.heartape.live.im.util;

/**
 * id生成器
 * @since 0.0.1
 * @author heartape
 */
public interface IdentifierGenerator<T> {

    /**
     * 创建id
     */
    T nextId();

}
