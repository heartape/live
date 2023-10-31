package com.heartape.live.im.interceptor.message;

import com.heartape.live.im.context.MessageContext;
import com.heartape.live.im.gateway.PurposeType;
import com.heartape.live.im.interceptor.Interceptor;
import com.heartape.live.im.send.ErrorSend;
import com.heartape.live.im.send.Send;

import java.util.Map;
import java.util.Set;

/**
 * 黑名单拦截器-内存实现
 * @since 0.0.1
 * @author heartape
 */
public class MemoryBlackListInterceptor implements Interceptor<MessageContext> {

    /**
     * DEFAULT_ERROR
     */
    private final static String DEFAULT_ERROR = "You are on the blacklist";

    /**
     * key 拉黑 value
     */
    private final Map<String, Set<String>> blackSetMap;

    public MemoryBlackListInterceptor(Map<String, Set<String>> blackSetMap) {
        this.blackSetMap = blackSetMap;
    }

    @Override
    public Send preSend(MessageContext messageContext) {
        String purposeType = messageContext.getPurposeType();
        if (PurposeType.PERSON.equals(purposeType)){
            String uid = messageContext.getUid();
            String purpose = messageContext.getPurpose();
            Set<String> idSet = this.blackSetMap.get(purpose);
            if (idSet != null && !idSet.isEmpty() && idSet.contains(uid)){
                return new ErrorSend(DEFAULT_ERROR);
            }
        }
        return null;
    }

    @Override
    public Send afterSend(MessageContext context, Send send) {
        return send;
    }

}
