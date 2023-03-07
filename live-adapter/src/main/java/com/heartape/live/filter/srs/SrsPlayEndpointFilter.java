package com.heartape.live.filter.srs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartape.live.facade.MediaHookFacade;
import com.heartape.live.http.RequestMatcher;
import com.heartape.live.http.ResponseConverter;
import com.heartape.live.http.SimpleConvertedResponse;
import com.heartape.live.http.StrictRequestMatcher;
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
import java.util.stream.Collectors;

/**
 * A Filter that processes play request
 * @since 0.1
 * @author heartape
 */
@Slf4j
public class SrsPlayEndpointFilter extends OncePerRequestFilter {

    private static final String DEFAULT_HOOK_PLAY_URI = "/live/hook/play";
    private final RequestMatcher requestMatcher;
    private final HttpMessageConverter<ResponseConverter> httpMessageConverter;
    private final MediaHookFacade mediaHookFacade;

    public SrsPlayEndpointFilter(HttpMessageConverter<ResponseConverter> httpMessageConverter, MediaHookFacade mediaHookFacade) {
        this(httpMessageConverter, DEFAULT_HOOK_PLAY_URI, mediaHookFacade);
    }

    public SrsPlayEndpointFilter(HttpMessageConverter<ResponseConverter> httpMessageConverter, String hookPlayUri, MediaHookFacade mediaHookFacade) {
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

        SrsPlayRequest req = SrsPlayRequest.fromHttpRequest(request);
        log.info(req.toString());

        boolean b = mediaHookFacade.play(req.server_id, req.client_id, req.app, req.stream, req.param);
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
    private static class SrsPlayRequest {

        private String server_id;
        private String action;
        private String client_id;
        private String ip;
        private String vhost;
        private String app;
        private String stream;
        private String pageUrl;
        private String param;

        public static SrsPlayRequest fromHttpRequest(HttpServletRequest request){
            try {
                String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(body, SrsPlayRequest.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
