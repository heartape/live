package com.heartape.live.im.util;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 分页信息
 * @param <T> 数据类型
 * @since 0.0.1
 * @author heartape
 */
@Getter
public class Page<T> implements Serializable {

    /**
     * 页码
     */
    private final int page;
    /**
     * 页宽
     */
    private final int size;
    /**
     * 总数
     */
    @Setter
    private long total;
    /**
     * list
     */
    @Setter
    private List<T> list;

    public Page(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public Page(int page, int size, long total, List<T> list) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.list = list;
    }

    public static <T> Page<T> empty(int page, int size){
        return new Page<>(page, size);
    }
}
