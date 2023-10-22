package com.heartape.live.streaming.code;

import com.heartape.live.streaming.http.DefaultResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import java.util.Map;

/**
 * http实现
 * @see CodeAuthenticationProvider
 */
@AllArgsConstructor
public class HttpCodeAuthenticationProvider implements CodeAuthenticationProvider {

    private final RestOperations restOperations;

    private final String codeAuthenticationUrl;

    @Override
    public boolean authenticate(String code, String stream) {
        Map<String, String> req = Map.of("code", code, "stream", stream);
        ResponseEntity<DefaultResponse> response = this.restOperations.postForEntity(codeAuthenticationUrl, req, DefaultResponse.class);
        return isOk(response);
    }

    /**
     * @param res 鉴权服务器响应
     * @return 是否正常
     */
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
