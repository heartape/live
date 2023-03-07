package com.heartape.live.facade;

/**
 * 流媒体服务器回调
 * @since 0.1
 * @author heartape
 */
public interface MediaHookFacade {

    /**
     * 是否允许推流
     */
    boolean publish(String server_id, String client_id, String app, String stream, String tcUrl);

    /**
     * 停止推流
     */
    void unPublish(String server_id, String client_id, String app, String stream);

    /**
     * 是否允许播放流
     */
    boolean play(String server_id, String client_id, String app, String stream, String param);

    /**
     * 停止播放
     */
    void stop(String server_id, String client_id, String app, String stream);
}
