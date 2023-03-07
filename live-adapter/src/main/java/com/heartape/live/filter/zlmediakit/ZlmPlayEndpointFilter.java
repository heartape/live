package com.heartape.live.filter.zlmediakit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartape.live.facade.MediaHookFacade;
import com.heartape.live.http.RequestMatcher;
import com.heartape.live.http.ResponseConverter;
import com.heartape.live.http.StrictRequestMatcher;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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

    private static final String DEFAULT_HOOK_PLAY_URI = "/live/hook/play";
    private final RequestMatcher requestMatcher;
    private final HttpMessageConverter<ResponseConverter> httpMessageConverter;
    private final MediaHookFacade mediaHookFacade;

    public ZlmPlayEndpointFilter(HttpMessageConverter<ResponseConverter> httpMessageConverter, MediaHookFacade mediaHookFacade) {
        this(httpMessageConverter, DEFAULT_HOOK_PLAY_URI, mediaHookFacade);
    }

    public ZlmPlayEndpointFilter(HttpMessageConverter<ResponseConverter> httpMessageConverter, String hookPlayUri, MediaHookFacade mediaHookFacade) {
        this.httpMessageConverter = httpMessageConverter;
        this.mediaHookFacade = mediaHookFacade;
        this.requestMatcher = new StrictRequestMatcher(hookPlayUri, HttpMethod.POST);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (!this.requestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        PlayRequest playRequest = objectMapper.readValue(body, PlayRequest.class);

        boolean b = mediaHookFacade.play(playRequest.mediaServerId, playRequest.id, playRequest.app, playRequest.stream, playRequest.params);
        PlayResponseConverter res = b ? PlayResponseConverter.success() : PlayResponseConverter.error(-1, "error");

        try(ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response)) {
            this.httpMessageConverter.write(res, MediaType.APPLICATION_JSON, httpResponse);
        }
    }

    @Getter
    @Setter
    @ToString
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
