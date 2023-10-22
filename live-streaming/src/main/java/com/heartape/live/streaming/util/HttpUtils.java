package com.heartape.live.streaming.util;

import java.util.HashMap;
import java.util.Map;

/**
 * http工具类
 */
public class HttpUtils {

    /**
     * token
     */
    public final static String TOKEN = "token";

    /**
     * 推流码
     */
    public final static String CODE = "code";

    /**
     * 参数解析
     * @param params url参数
     * @return Map格式的url参数
     */
    public static Map<String, String> paramMap(String params){
        Map<String, String> paramMap = new HashMap<>();
        if (StringUtils.hasText(params) && params.startsWith("?")){
            String paramPair = params.substring(1);
            if (paramPair.contains("&")){
                for (String parameter : paramPair.split("&")) {
                    int index = parameter.indexOf('=');
                    paramMap.put(parameter.substring(0, index), parameter.substring(index + 1));
                }
            } else {
                int index = paramPair.indexOf('=');
                paramMap.put(paramPair.substring(0, index), paramPair.substring(index + 1));
            }
        }
        return paramMap;
    }

    /**
     * @param url for example: rtmp://127.0.0.1/live
     * @return rtmp
     */
    public static String getProtocol(String url){
        return url.substring(0, url.indexOf(':'));
    }

    /**
     * @param url for example: rtmp://127.0.0.1/live
     * @return 127.0.0.1
     */
    public static String getHost(String url){
        String s1 = url.substring(url.indexOf("://") + 3);
        return s1.substring(0, s1.indexOf('/'));
    }

}
