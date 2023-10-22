package com.heartape.live.streaming.http;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.NonNull;

import java.util.Map;

/**
 * http响应转换，用于序列化流媒体服务器响应
 */
public class ConvertedResponseHttpMessageConverter extends AbstractHttpMessageConverter<ResponseConverter> {

    /**
     * json转换器
     */
    private final GenericHttpMessageConverter<Object> jsonMessageConverter = new MappingJackson2HttpMessageConverter();

    /**
     * 写入的数据类型
     */
    private static final ParameterizedTypeReference<Map<String, Object>> STRING_OBJECT_MAP = new ParameterizedTypeReference<>() {
    };

    @Override
    protected boolean supports(@NonNull Class<?> clazz) {
        return ResponseConverter.class.isAssignableFrom(clazz);
    }

    /**
     * @throws HttpMessageNotReadableException 当前只支持写入
     */
    @NonNull
    @Override
    protected ResponseConverter readInternal(@NonNull Class<? extends ResponseConverter> clazz, @NonNull HttpInputMessage inputMessage) throws HttpMessageNotReadableException {
        throw new HttpMessageNotReadableException("method not support", inputMessage);
    }

    @Override
    protected void writeInternal(@NonNull ResponseConverter responseConverter, @NonNull HttpOutputMessage outputMessage) throws HttpMessageNotWritableException {
        try {
            Map<String, Object> tokenResponseParameters = responseConverter.parameters();
            this.jsonMessageConverter.write(tokenResponseParameters, STRING_OBJECT_MAP.getType(),
                    MediaType.APPLICATION_JSON, outputMessage);
        }
        catch (Exception ex) {
            throw new HttpMessageNotWritableException(
                    "An error occurred writing the Third Party Streaming Media Response: " + ex.getMessage(), ex);
        }
    }
}
