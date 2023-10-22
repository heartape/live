package com.heartape.live.streaming.facade;

import java.util.Map;

/**
 * 流媒体服务器回调处理
 * @since 0.1
 * @author heartape
 */
public interface LiveStreamingHookFacade {

    /**
     * 推流鉴权
     * @param protocol 协议
     * @param app 应用
     * @param stream 流
     * @param param 参数
     * @param serverId 服务器id
     * @param clientId 客户端id
     * @param ip 客户端ip
     * @return 是否允许
     */
    boolean publish(String protocol, String app, String stream, Map<String, String> param, String serverId, String clientId, String ip);

    /**
     * 播放鉴权
     * @param app 应用
     * @param stream 流
     * @param param 参数
     * @param serverId 服务器id
     * @param clientId 客户端id
     * @param ip 客户端ip
     * @return 是否允许
     */
    boolean play(String app, String stream, Map<String, String> param, String serverId, String clientId, String ip);

}
