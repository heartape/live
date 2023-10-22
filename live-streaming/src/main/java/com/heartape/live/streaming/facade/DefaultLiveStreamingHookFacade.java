package com.heartape.live.streaming.facade;

import com.heartape.live.streaming.code.CodeAuthenticationProvider;
import com.heartape.live.streaming.filter.LiveStreamingScope;
import com.heartape.live.streaming.util.HttpUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.util.Map;

/**
 * 通过查询本地数据处理回调
 */
@Slf4j
@AllArgsConstructor
public class DefaultLiveStreamingHookFacade implements LiveStreamingHookFacade {

    private final JwtDecoder jwtDecoder;

    private final CodeAuthenticationProvider codeAuthenticationProvider;

    @Override
    public boolean publish(String protocol, String app, String stream, Map<String, String> param, String serverId, String clientId, String ip) {
        log.info("protocol:{},app:{},stream:{} => publish", protocol, app, stream);
        String code = param.get(HttpUtils.CODE);
        return codeAuthenticationProvider.authenticate(code, stream);
    }

    @Override
    public boolean play(String app, String stream, Map<String, String> param, String serverId, String clientId, String ip) {
        log.info("app:{},stream:{} => play", app, stream);
        String token = param.get(HttpUtils.TOKEN);
        try {
            Jwt jwt = this.jwtDecoder.decode(token);
            return jwt.getClaimAsStringList(LiveStreamingScope.SCOPE).contains(LiveStreamingScope.PUBLISH);
        } catch (Exception e){
            return false;
        }
    }
}
