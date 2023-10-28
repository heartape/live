package com.heartape.live.bullet.config;

import com.heartape.live.bullet.filter.LiveRoomLoadFilter;
import com.heartape.live.bullet.filter.LiveRoomStatusFilter;
import com.heartape.live.bullet.filter.MemoryBlackListFilter;
import com.heartape.live.bullet.flow.FlowManager;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@Data
@ConfigurationProperties("live.bullet")
public class LiveBulletProperties {

    private Flow flow;

    private Filter filter;

    /**
     * @see FlowManager
     */
    @Getter
    @Setter
    public static class Flow {
        /**
         * Flow初始数量
         */
        private Integer init = 2;
        /**
         * Flow最大数量
         */
        private Integer max = 6;
    }

    @Getter
    @Setter
    public static class Filter {
        /**
         * 黑名单，仅用于开发测试
         * @see MemoryBlackListFilter
         */
        @Deprecated
        private List<String> black;

        /**
         * 开启房间，仅用于开发测试
         * @see LiveRoomStatusFilter
         */
        @Deprecated
        private List<String> room;

        /**
         * 最大长连接数
         * @see LiveRoomLoadFilter
         */
        private Integer maxLong = 10000;
    }

}
