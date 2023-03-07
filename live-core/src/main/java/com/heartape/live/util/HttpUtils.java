package com.heartape.live.util;

import java.util.HashMap;
import java.util.Map;

public class HttpUtils {

    public final static String TOKEN = "token";

    public static Map<String, String> paramMap(String params){
        Map<String, String> paramMap = new HashMap<>();
        if (StringUtils.hasText(params)){
            if (params.contains("&")){
                for (String parameter : params.split("&")) {
                    int index = parameter.indexOf('=');
                    paramMap.put(parameter.substring(0, index), parameter.substring(index + 1));
                }
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
