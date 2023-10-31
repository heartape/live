package com.heartape.live.im.controller;

import com.heartape.live.im.manage.friend.FriendshipService;
import com.heartape.live.im.manage.group.GroupChat;
import com.heartape.live.im.manage.group.GroupChatMember;
import com.heartape.live.im.manage.group.GroupChatMemberRepository;
import com.heartape.live.im.manage.group.GroupChatRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/manage")
public class ManageController {

    private final FriendshipService friendshipService;

    private final GroupChatRepository groupChatRepository;

    private final GroupChatMemberRepository groupChatMemberRepository;

    @PostConstruct
    public void initGroupAndFriend() {
        this.friendshipService.addFriend("1111", "2222", null, null);
        this.friendshipService.addFriend("1111", "3333", null, null);
        this.friendshipService.addBlackList("1111", "3333");

        GroupChat groupChat1 = new GroupChat("111", "测试群聊1", "https://www.baidu.com/img/bd_logo1.png", 1, List.of("爱好", "学习"), LocalDateTime.now());
        this.groupChatRepository.save(groupChat1);

        GroupChat groupChat2 = new GroupChat("222", "测试群聊2", "https://www.baidu.com/img/bd_logo1.png", 1, List.of("爱好", "学习"), LocalDateTime.now());
        this.groupChatRepository.save(groupChat2);

        GroupChatMember groupChatMember11 = new GroupChatMember("1", "1111", "111", 1, 1, LocalDateTime.now());
        groupChatMemberRepository.save(groupChatMember11);

        GroupChatMember groupChatMember12 = new GroupChatMember("2", "2222", "111", 2, 1, LocalDateTime.now());
        groupChatMemberRepository.save(groupChatMember12);

        GroupChatMember groupChatMember21 = new GroupChatMember("3", "1111", "222", 2, 1, LocalDateTime.now());
        groupChatMemberRepository.save(groupChatMember21);

        GroupChatMember groupChatMember22 = new GroupChatMember("4", "2222", "222", 1, 1, LocalDateTime.now());
        groupChatMemberRepository.save(groupChatMember22);
    }

}
