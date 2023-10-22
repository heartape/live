package com.heartape.live.streaming.http;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class SimpleConvertedResponse implements ResponseConverter {

    private Integer code;
    private String msg;

    public static SimpleConvertedResponse success(){
        return SimpleConvertedResponse
                .builder()
                .code(0)
                .msg("success")
                .build();
    }

    public static SimpleConvertedResponse error(int code, String msg){
        return SimpleConvertedResponse
                .builder()
                .code(code)
                .msg(msg)
                .build();
    }

    @Override
    public Map<String, Object> parameters() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("code", this.code);
        parameters.put("msg", this.msg);
        return parameters;
    }
}
