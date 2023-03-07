package com.heartape.live.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartape.live.http.RequestMatcher;
import com.heartape.live.http.ResponseConverter;
import com.heartape.live.http.SimpleConvertedResponse;
import com.heartape.live.http.StrictRequestMatcher;
import com.heartape.live.scope.UserScope;
import com.heartape.live.scope.UserScopeRepository;
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
public class UserScopeSetEndpointFilter extends OncePerRequestFilter {

    private static final String DEFAULT_HOOK_PLAY_URI = "/live/manage/scope/set";
    private final RequestMatcher requestMatcher;
    private final HttpMessageConverter<ResponseConverter> httpMessageConverter;

    private final UserScopeRepository userScopeRepository;

    public UserScopeSetEndpointFilter(HttpMessageConverter<ResponseConverter> httpMessageConverter, UserScopeRepository userScopeRepository) {
        this(httpMessageConverter, DEFAULT_HOOK_PLAY_URI, userScopeRepository);
    }

    public UserScopeSetEndpointFilter(HttpMessageConverter<ResponseConverter> httpMessageConverter, String hookPlayUri, UserScopeRepository userScopeRepository) {
        this.httpMessageConverter = httpMessageConverter;
        this.userScopeRepository = userScopeRepository;
        this.requestMatcher = new StrictRequestMatcher(hookPlayUri, HttpMethod.POST);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (!this.requestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        UserScope userScope = userScope(request);
        userScopeRepository.insert(userScope);
        ResponseConverter res = SimpleConvertedResponse.success();

        try(ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response)) {
            this.httpMessageConverter.write(res, MediaType.APPLICATION_JSON, httpResponse);
        }
    }

    private UserScope userScope(HttpServletRequest request){
        try {
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(body, UserScope.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("请求数据异常");
        }
    }
}
