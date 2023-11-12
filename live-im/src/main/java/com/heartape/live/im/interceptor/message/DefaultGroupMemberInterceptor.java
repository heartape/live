package com.heartape.live.im.interceptor.message;

import com.heartape.live.im.context.MessageContext;
import com.heartape.live.im.gateway.PurposeType;
import com.heartape.live.im.interceptor.Interceptor;
import com.heartape.live.im.manage.group.GroupChatMemberRepository;
import com.heartape.live.im.send.ErrorSend;
import com.heartape.live.im.send.Send;
import lombok.AllArgsConstructor;

/**
 * group关系拦截器-内存实现
 * @since 0.0.1
 * @author heartape
 */
@AllArgsConstructor
public class DefaultGroupMemberInterceptor implements Interceptor<MessageContext> {

    /**
     * JOIN_ERROR
     */
    private final static String JOIN_ERROR = "Not yet joined the group chat";

    /**
     * key 拉黑 value
     */
    private final GroupChatMemberRepository groupChatMemberRepository;

    @Override
    public Send preSend(MessageContext messageContext) {
        String purposeType = messageContext.getPurposeType();
        if (PurposeType.GROUP.equals(purposeType)){
            String groupId = messageContext.getPurpose();
            String uid = messageContext.getUid();
            boolean member = groupChatMemberRepository.exist(groupId, uid);
            if (!member){
                return new ErrorSend(JOIN_ERROR);
            }
        }
        return null;
    }

    @Override
    public Send afterSend(MessageContext context, Send send) {
        return send;
    }
}
