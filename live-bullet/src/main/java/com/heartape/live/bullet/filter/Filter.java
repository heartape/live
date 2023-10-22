package com.heartape.live.bullet.filter;

/**
 * 弹幕过滤器
 */
public interface Filter<T> {
    boolean permit(T t);

}
