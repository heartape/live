package com.heartape.live.im.message.base;

import com.heartape.live.im.message.Message;
import com.heartape.live.im.message.filter.Filter;
import com.heartape.live.im.message.filter.FilterManager;
import com.heartape.live.im.send.Send;

import java.util.ArrayList;
import java.util.List;

/**
 * @see FilterManager
 * @since 0.0.1
 * @author heartape
 */
public class BaseFilterManager<T extends Message> implements FilterManager<T> {

    private final List<Filter<T>> filters;

    public BaseFilterManager() {
        this.filters = new ArrayList<>();
    }

    @Override
    public void register(Filter<T> filter) {
        this.filters.add(filter);
    }

    @Override
    public Send doFilter(T message) {
        for (Filter<T> filter : filters) {
            Send send = filter.doFilter(message);
            if (send != null){
                return send;
            }
        }
        return null;
    }
}
