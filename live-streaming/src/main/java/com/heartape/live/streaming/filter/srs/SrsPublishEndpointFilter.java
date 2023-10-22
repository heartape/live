package com.heartape.live.streaming.filter.srs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartape.live.streaming.exception.RemoteRequestException;
import com.heartape.live.streaming.facade.LiveStreamingHookFacade;
import com.heartape.live.streaming.http.RequestMatcher;
import com.heartape.live.streaming.http.ResponseConverter;
import com.heartape.live.streaming.http.SimpleConvertedResponse;
import com.heartape.live.streaming.http.StrictRequestMatcher;
import com.heartape.live.streaming.util.HttpUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A Filter that processes publish request
 * @since 0.1
 * @author heartape
 */
@Slf4j
public class SrsPublishEndpointFilter extends OncePerRequestFilter {

    private static final String DEFAULT_HOOK_PUBLISH_URI = "/live/streaming/hook/publish";
    private final RequestMatcher requestMatcher;
    private final HttpMessageConverter<ResponseConverter> httpMessageConverter;
    private final LiveStreamingHookFacade liveStreamingHookFacade;

    public SrsPublishEndpointFilter(HttpMessageConverter<ResponseConverter> httpMessageConverter, LiveStreamingHookFacade liveStreamingHookFacade) {
        this(httpMessageConverter, DEFAULT_HOOK_PUBLISH_URI, liveStreamingHookFacade);
    }

    public SrsPublishEndpointFilter(HttpMessageConverter<ResponseConverter> httpMessageConverter, String hookPublishUri, LiveStreamingHookFacade liveStreamingHookFacade) {
        this.httpMessageConverter = httpMessageConverter;
        this.liveStreamingHookFacade = liveStreamingHookFacade;
        this.requestMatcher = new StrictRequestMatcher(hookPublishUri, HttpMethod.POST);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (!this.requestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        SrsPublishRequest req = SrsPublishRequest.fromHttpRequest(request);
        String protocol = HttpUtils.getProtocol(req.tcUrl);
        Map<String, String> paramMap = HttpUtils.paramMap(req.param);

        boolean b = liveStreamingHookFacade.publish(protocol, req.app, req.stream, paramMap, req.server_id, req.client_id, req.ip);
        SimpleConvertedResponse res = b ? SimpleConvertedResponse.success() : SimpleConvertedResponse.error(-1, "error");

        try(ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response)) {
            this.httpMessageConverter.write(res, MediaType.APPLICATION_JSON, httpResponse);
        }
    }

    /**
     * srs流媒体服务器HTTP Callback Request
     */
    @Getter
    @Setter
    @ToString
    private static class SrsPublishRequest {

        private String server_id;
        private String action;
        private String client_id;
        private String ip;
        private String vhost;
        private String app;
        private String stream;
        private String tcUrl;
        private String param;

        public static SrsPublishRequest fromHttpRequest(HttpServletRequest request){
            try {
                String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(body, SrsPublishRequest.class);
            } catch (IOException e) {
                throw new RemoteRequestException("uri:" + request.getRequestURI());
            }
        }
    }
}
