package com.heartape.live.im.interceptor.message;

import com.heartape.live.im.context.MessageContext;
import com.heartape.live.im.gateway.PurposeType;
import com.heartape.live.im.interceptor.Interceptor;
import com.heartape.live.im.manage.friend.FriendshipService;
import com.heartape.live.im.send.ErrorSend;
import com.heartape.live.im.send.Send;
import lombok.AllArgsConstructor;

/**
 * 好友关系拦截器-内存实现
 * @since 0.0.1
 * @author heartape
 */
@AllArgsConstructor
public class MemoryFriendshipInterceptor implements Interceptor<MessageContext> {

    /**
     * DEFAULT_ERROR
     */
    private final static String DEFAULT_ERROR = "You haven't added each other as friends yet";

    /**
     * 好友关系
     */
    private final FriendshipService friendshipService;

    @Override
    public Send preSend(MessageContext messageContext) {
        String purposeType = messageContext.getPurposeType();
        if (PurposeType.PERSON.equals(purposeType)){
            boolean friend = this.friendshipService.isFriend(messageContext.getUid(), messageContext.getPurpose());
            if (!friend){
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
