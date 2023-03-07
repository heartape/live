package com.heartape.live.facade;

import com.heartape.live.code.PublishCodeRepository;
import com.heartape.live.scope.LiveScope;
import com.heartape.live.scope.UserScopeRepository;
import com.heartape.live.util.HttpUtils;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 通过查询本地数据处理回调
 */
@AllArgsConstructor
public class RepositoryMediaHookFacade implements MediaHookFacade {

    private final PublishCodeRepository publishCodeRepository;

    private final UserScopeRepository userScopeRepository;

    /**
     * 流媒体推流协议
     */
    private final String protocol;
    /**
     * 流媒体推流ip
     */
    private final String host;
    /**
     * 流媒体推流路径
     */
    private final String app;

    @Override
    public boolean publish(String server_id, String client_id, String app, String stream, String tcUrl) {
        String protocol = HttpUtils.getProtocol(tcUrl);
        if (!Objects.equals(this.protocol, protocol)){
            return false;
        }
        String host = HttpUtils.getHost(tcUrl);
        if (!Objects.equals(this.host, host)){
            return false;
        }
        if (!Objects.equals(this.app, app)){
            return false;
        }
        // check stream
        String id = publishCodeRepository.select(stream);
        List<String> scopes = userScopeRepository.select(id);
        return scopes != null && scopes.contains(LiveScope.PUBLISH.name());
    }

    @Override
    public void unPublish(String server_id, String client_id, String app, String stream) {

    }

    @Override
    public boolean play(String server_id, String client_id, String app, String stream, String param) {
        Map<String, String> paramMap = HttpUtils.paramMap(param);
        String token = paramMap.get(HttpUtils.TOKEN);
        return userScopeRepository.check(token, LiveScope.PLAY);
    }

    @Override
    public void stop(String server_id, String client_id, String app, String stream) {

    }
}
