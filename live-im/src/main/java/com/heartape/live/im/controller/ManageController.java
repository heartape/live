package com.heartape.live.im.controller;

import com.heartape.live.im.config.MessageConfigurer;
import com.heartape.live.im.manage.friend.FriendshipService;
import com.heartape.live.im.manage.group.GroupChatService;
import com.heartape.live.im.manage.group.apply.GroupChatApply;
import com.heartape.live.im.manage.group.info.GroupChatSimpleInfo;
import com.heartape.live.im.message.type.text.MemoryTextKeywordShieldFilter;
import com.heartape.live.im.message.type.text.TextBaseFilter;
import com.heartape.live.im.message.type.text.TextFilterManager;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/manage")
public class ManageController {

    private final FriendshipService friendshipService;

    private final GroupChatService groupChatService;

    private final MessageConfigurer messageConfigurer;

    @PostConstruct
    public void initGroupAndFriend() {
        this.friendshipService.addFriend("1111", "2222", null, null);
        this.friendshipService.addFriend("1111", "3333", null, null);
        this.friendshipService.addBlackList("1111", "3333");
        this.groupChatService.createGroupChat("1111", "测试群聊1", "https://www.baidu.com/img/bd_logo1.png", 1, 1, List.of("爱好", "学习"));
        this.groupChatService.createGroupChat("1111", "测试群聊2", "https://www.baidu.com/img/bd_logo1.png", 1, 1, List.of("爱好", "学习"));

        List<GroupChatSimpleInfo> groupChatList = this.groupChatService.getGroupChatList("1111");

        GroupChatSimpleInfo groupChatSimpleInfo0 = groupChatList.get(0);
        String id0 = groupChatSimpleInfo0.getId();
        log.debug("init group id0: {}", id0);

        GroupChatSimpleInfo groupChatSimpleInfo1 = groupChatList.get(1);
        String id1 = groupChatSimpleInfo1.getId();
        log.debug("init group id1: {}", id1);

        this.groupChatService.applyJoinGroupChat("2222", id0);
        this.groupChatService.applyJoinGroupChat("2222", id1);

        List<GroupChatApply> groupChatApplyList = this.groupChatService.getGroupChatApplyList("1111");

        GroupChatApply groupChatApply0 = groupChatApplyList.get(0);
        this.groupChatService.agreeJoinGroupChat("1111", groupChatApply0.getId());

        GroupChatApply groupChatApply1 = groupChatApplyList.get(1);
        this.groupChatService.agreeJoinGroupChat("1111", groupChatApply1.getId());

        TextFilterManager textFilterManager = new TextFilterManager();
        textFilterManager.register(new TextBaseFilter());
        textFilterManager.register(new MemoryTextKeywordShieldFilter(Set.of("卧槽", "握草")));

        this.messageConfigurer.text().filterManager(textFilterManager);
    }

}
