package com.heartape.live.facade;

import com.heartape.live.http.DefaultResponse;
import com.heartape.live.util.HttpUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

/**
 * 通过http请求处理回调
 */
@AllArgsConstructor
public class HttpMediaHookFacade implements MediaHookFacade {

    private final RestOperations restOperations;

    private final String publishCheckUrl;

    private final String playCheckUrl;

    @SuppressWarnings("rawtypes")
    @Override
    public boolean publish(String server_id, String client_id, String app, String stream, String tcUrl) {
        String protocol = HttpUtils.getProtocol(tcUrl);
        String host = HttpUtils.getHost(tcUrl);
        Map<String, String> paramMap = Map.of("protocol", protocol, "host", host, "app", app, "stream", stream);
        ResponseEntity<DefaultResponse> res = this.restOperations.getForEntity(publishCheckUrl, DefaultResponse.class, paramMap);
        return isOk(res);
    }

    @Override
    public void unPublish(String server_id, String client_id, String app, String stream) {

    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean play(String server_id, String client_id, String app, String stream, String param) {
        Map<String, String> paramMap = HttpUtils.paramMap(param);
        String token = paramMap.get(HttpUtils.TOKEN);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        URI uri = UriComponentsBuilder
                .fromUriString(playCheckUrl)
                .build()
                .toUri();
        RequestEntity<Void> req = new RequestEntity<>(httpHeaders, HttpMethod.GET, uri);
        ResponseEntity<DefaultResponse> res = this.restOperations.exchange(req, DefaultResponse.class);
        return isOk(res);
    }

    @Override
    public void stop(String server_id, String client_id, String app, String stream) {

    }

    @SuppressWarnings("rawtypes")
    private boolean isOk(ResponseEntity<DefaultResponse> res){
        if (!res.getStatusCode().is2xxSuccessful()){
            return false;
        }
        DefaultResponse response = res.getBody();
        if (response == null){
            return false;
        }
        Integer code = response.getCode();
        return code != null && code == 0;
    }
}
