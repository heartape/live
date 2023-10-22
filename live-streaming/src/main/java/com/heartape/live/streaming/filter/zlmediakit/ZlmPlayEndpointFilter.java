package com.heartape.live.streaming.filter.zlmediakit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartape.live.streaming.exception.RemoteRequestException;
import com.heartape.live.streaming.facade.LiveStreamingHookFacade;
import com.heartape.live.streaming.http.RequestMatcher;
import com.heartape.live.streaming.http.ResponseConverter;
import com.heartape.live.streaming.http.StrictRequestMatcher;
import com.heartape.live.streaming.util.HttpUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A Filter that processes play request
 * @since 0.1
 * @author heartape
 */
@Slf4j
public class ZlmPlayEndpointFilter extends OncePerRequestFilter {

    private static final String DEFAULT_HOOK_PLAY_URI = "/live/streaming/hook/play";
    private final RequestMatcher requestMatcher;
    private final HttpMessageConverter<ResponseConverter> httpMessageConverter;
    private final LiveStreamingHookFacade liveStreamingHookFacade;

    public ZlmPlayEndpointFilter(HttpMessageConverter<ResponseConverter> httpMessageConverter, LiveStreamingHookFacade liveStreamingHookFacade) {
        this(httpMessageConverter, DEFAULT_HOOK_PLAY_URI, liveStreamingHookFacade);
    }

    public ZlmPlayEndpointFilter(HttpMessageConverter<ResponseConverter> httpMessageConverter, String hookPlayUri, LiveStreamingHookFacade liveStreamingHookFacade) {
        this.httpMessageConverter = httpMessageConverter;
        this.liveStreamingHookFacade = liveStreamingHookFacade;
        this.requestMatcher = new StrictRequestMatcher(hookPlayUri, HttpMethod.POST);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (!this.requestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        PlayRequest playRequest = PlayRequest.fromHttpRequest(request);
        Map<String, String> paramMap = HttpUtils.paramMap(playRequest.params);

        boolean b = liveStreamingHookFacade.play(playRequest.app, playRequest.stream, paramMap, playRequest.mediaServerId, playRequest.id, playRequest.ip);
        PlayResponseConverter res = b ? PlayResponseConverter.success() : PlayResponseConverter.error(-1, "error");

        try(ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response)) {
            this.httpMessageConverter.write(res, MediaType.APPLICATION_JSON, httpResponse);
        }
    }

    @Getter
    @Setter
    private static class PlayRequest {

        /**
         * 服务器id,通过配置文件设置
         */
        private String mediaServerId;
        /**
         * 流应用名
         */
        private String app;
        /**
         * TCP链接唯一ID
         */
        private String id;
        /**
         * 推流器ip
         */
        private String ip;

        private String originType;

        private String originTypeStr;
        /**
         * 推流url参数
         */
        private String params;
        /**
         * 推流器端口号
         */
        private Integer port;
        /**
         * 推流的协议,rtsp、rtmp等
         */
        private String schema;
        /**
         * 流ID
         */
        private String stream;
        /**
         * 流虚拟主机
         */
        private String vhost;

        public static PlayRequest fromHttpRequest(HttpServletRequest request){
            try {
                String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(body, PlayRequest.class);
            } catch (IOException e) {
                throw new RemoteRequestException("uri:" + request.getRequestURI());
            }
        }

    }

    @Getter
    @Setter
    @Builder
    private static class PlayResponseConverter implements ResponseConverter {

        private Integer code;
        private String msg;

        public static PlayResponseConverter success(){
            return PlayResponseConverter
                    .builder()
                    .code(0)
                    .msg("success")
                    .build();
        }

        public static PlayResponseConverter error(int code, String msg){
            return PlayResponseConverter
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
}
