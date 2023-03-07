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
 * A Filter that processes publish request
 * @since 0.1
 * @author heartape
 */
@Slf4j
public class ZlmPublishEndpointFilter extends OncePerRequestFilter {

    private static final String DEFAULT_HOOK_PUBLISH_URI = "/live/hook/publish";
    private final RequestMatcher requestMatcher;
    private final HttpMessageConverter<ResponseConverter> httpMessageConverter;
    private final MediaHookFacade mediaHookFacade;

    public ZlmPublishEndpointFilter(HttpMessageConverter<ResponseConverter> httpMessageConverter, MediaHookFacade mediaHookFacade) {
        this(httpMessageConverter, DEFAULT_HOOK_PUBLISH_URI, mediaHookFacade);
    }

    public ZlmPublishEndpointFilter(HttpMessageConverter<ResponseConverter> httpMessageConverter, String hookPublishUri, MediaHookFacade mediaHookFacade) {
        this.httpMessageConverter = httpMessageConverter;
        this.mediaHookFacade = mediaHookFacade;
        this.requestMatcher = new StrictRequestMatcher(hookPublishUri, HttpMethod.POST);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (!this.requestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        PublishRequest publishRequest = objectMapper.readValue(body, PublishRequest.class);

        boolean b = mediaHookFacade.publish(publishRequest.mediaServerId, publishRequest.id, publishRequest.app, publishRequest.stream, "");
        PublishResponseConverter res = b ? PublishResponseConverter.success() : PublishResponseConverter.error(-1, "error");

        try(ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response)) {
            this.httpMessageConverter.write(res, MediaType.APPLICATION_JSON, httpResponse);
        }
    }

    @Getter
    @Setter
    @ToString
    private static class PublishRequest {
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
    @ToString
    @Builder
    private static class PublishResponseConverter implements ResponseConverter {
        private Integer code;
        private String msg;

        private boolean enable_hls;
        private boolean enable_mp4;
        private boolean enable_rtsp;
        private boolean enable_rtmp;
        private boolean enable_ts;
        private boolean enable_fmp4;
        private boolean enable_audio;
        private boolean add_mute_audio;

        private String mp4_save_path;
        private Integer mp4_max_second;

        private String hls_save_path;

        public static PublishResponseConverter success() {
            return PublishResponseConverter
                    .builder()
                    .code(0)
                    .msg("success")
                    .build();
        }

        public static PublishResponseConverter error(int code, String msg) {
            return PublishResponseConverter
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
            parameters.put("enable_hls", this.enable_hls);
            parameters.put("enable_mp4", this.enable_mp4);
            parameters.put("enable_rtsp", this.enable_rtsp);
            parameters.put("enable_rtmp", this.enable_rtmp);
            parameters.put("enable_ts", this.enable_ts);
            parameters.put("enable_fmp4", this.enable_fmp4);
            parameters.put("enable_audio", this.enable_audio);
            parameters.put("add_mute_audio", this.add_mute_audio);
            parameters.put("mp4_save_path", this.mp4_save_path);
            parameters.put("mp4_max_second", this.mp4_max_second);
            parameters.put("hls_save_path", this.hls_save_path);
            return parameters;
        }
    }
}
