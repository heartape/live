package com.heartape.live.im.controller;

import com.heartape.live.im.config.LiveImAutoConfiguration;
import com.heartape.live.im.message.base.BaseMessage;
import com.heartape.live.im.message.center.CenterMessageRepository;
import com.heartape.live.im.util.Page;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Qualifier(LiveImAutoConfiguration.GROUP_CENTER_MESSAGE_REPOSITORY_BEAN_NAME)
    private final CenterMessageRepository groupCenterMessageRepository;

    @Qualifier(LiveImAutoConfiguration.USER_CENTER_MESSAGE_REPOSITORY_BEAN_NAME)
    private final CenterMessageRepository userCenterMessageRepository;

    public MessageController(CenterMessageRepository groupCenterMessageRepository, CenterMessageRepository userCenterMessageRepository) {
        this.groupCenterMessageRepository = groupCenterMessageRepository;
        this.userCenterMessageRepository = userCenterMessageRepository;
    }

    @GetMapping("/sync/user")
    public Page<BaseMessage> syncUser(@RequestParam(required = false) Long id,
                                      @RequestParam String uid,
                                      @RequestParam String userId,
                                      @RequestParam Integer page){
        if (id == null){
            return this.userCenterMessageRepository.findByPurposeId(uid, userId, page, 100);
        }
        return this.userCenterMessageRepository.findByStartId(id, uid, userId, page, 100);
    }

    @GetMapping("/sync/group")
    public Page<BaseMessage> syncGroup(@RequestParam(required = false) Long id,
                                       @RequestParam String uid,
                                       @RequestParam String groupId,
                                       @RequestParam Integer page){
        if (id == null){
            return this.groupCenterMessageRepository.findByPurposeId(uid, groupId, page, 100);
        }
        return this.groupCenterMessageRepository.findByStartId(id, uid, groupId, page, 100);
    }

}
