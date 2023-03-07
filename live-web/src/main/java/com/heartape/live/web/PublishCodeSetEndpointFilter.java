package com.heartape.live.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartape.live.code.PublishCode;
import com.heartape.live.code.PublishCodeRepository;
import com.heartape.live.http.RequestMatcher;
import com.heartape.live.http.ResponseConverter;
import com.heartape.live.http.SimpleConvertedResponse;
import com.heartape.live.http.StrictRequestMatcher;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
 * A Filter that processes PublishCodeRepository
 * @since 0.1
 * @author heartape
 */
@Slf4j
public class PublishCodeSetEndpointFilter extends OncePerRequestFilter {

    private static final String DEFAULT_HOOK_PLAY_URI = "/live/manage/code/set";
    private final RequestMatcher requestMatcher;
    private final HttpMessageConverter<ResponseConverter> httpMessageConverter;
    private final PublishCodeRepository publishCodeRepository;

    public PublishCodeSetEndpointFilter(HttpMessageConverter<ResponseConverter> httpMessageConverter, PublishCodeRepository publishCodeRepository) {
        this(httpMessageConverter, DEFAULT_HOOK_PLAY_URI, publishCodeRepository);
    }

    public PublishCodeSetEndpointFilter(HttpMessageConverter<ResponseConverter> httpMessageConverter, String hookPlayUri, PublishCodeRepository publishCodeRepository) {
        this.httpMessageConverter = httpMessageConverter;
        this.publishCodeRepository = publishCodeRepository;
        this.requestMatcher = new StrictRequestMatcher(hookPlayUri, HttpMethod.POST);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (!this.requestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        PublishCode publishCode = publishCode(request);
        publishCodeRepository.insert(publishCode);
        ResponseConverter res = SimpleConvertedResponse.success();

        try(ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response)) {
            this.httpMessageConverter.write(res, MediaType.APPLICATION_JSON, httpResponse);
        }
    }

    private PublishCode publishCode(HttpServletRequest request){
        try {
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(body, PublishCode.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("请求数据异常");
        }
    }
}
