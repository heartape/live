package com.heartape.live.bullet.filter;

import com.heartape.live.bullet.repository.bullet.Bullet;

import java.util.ArrayList;
import java.util.List;

public class SimpleSerialFilterChain implements FilterChain<Bullet> {

    private final List<Filter<Bullet>> filters;

    private SimpleSerialFilterChain(List<Filter<Bullet>> filters) {
        this.filters = filters;
    }

    @Override
    public boolean permit(Bullet bullet) {
        for (Filter<Bullet> filter : this.filters) {
            if (!filter.permit(bullet)){
                return false;
            }
        }
        return true;
    }

    public static SimpleSerialFilterChainBuilder builder() {
        return new SimpleSerialFilterChainBuilder();
    }

    public static class SimpleSerialFilterChainBuilder {
        private final List<Filter<Bullet>> filters;

        SimpleSerialFilterChainBuilder() {
            this.filters = new ArrayList<>();
        }

        public SimpleSerialFilterChainBuilder filter(Filter<Bullet> filter) {
            filters.add(filter);
            return this;
        }

        public SimpleSerialFilterChain build() {
            return new SimpleSerialFilterChain(this.filters);
        }
    }
}
