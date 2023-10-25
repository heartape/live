package com.heartape.live.ums.controller;

import com.heartape.exception.SystemInnerException;
import com.heartape.live.ums.user.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserLoginController {

    private final UserRepository userRepository;

    private final VerificationCodeManager verificationCodeManager;

    @GetMapping("/username")
    public User username(@RequestParam String username) {
        QUser qUser = QUser.user;
        Optional<com.heartape.live.ums.user.User> optional = userRepository.findOne(qUser.username.eq(username));
        if (optional.isEmpty()) {
            return null;
        }
        com.heartape.live.ums.user.User user = optional.get();
        UserStatus userStatus = user.getStatus();
        if (userStatus == UserStatus.NORMAL) {
            return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
        } else if (userStatus == UserStatus.LOCKED) {
            return new User(user.getUsername(), user.getPassword(), false, true, true, false, new ArrayList<>());
        } else if (userStatus == UserStatus.DISABLED) {
            return new User(user.getUsername(), user.getPassword(), false, false, true, true, new ArrayList<>());
        } else {
            throw new SystemInnerException();
        }
    }

    @GetMapping("/phone")
    public User checkCode(@RequestParam String phone) {
        String query = verificationCodeManager.query(phone);
        QUser qUser = QUser.user;
        Optional<com.heartape.live.ums.user.User> optional = userRepository.findOne(qUser.phone.eq(phone));
        if (optional.isEmpty()) {
            return null;
        }
        com.heartape.live.ums.user.User user = optional.get();
        UserStatus userStatus = user.getStatus();
        if (userStatus == UserStatus.NORMAL) {
            return new User(user.getPhone(), query, new ArrayList<>());
        } else if (userStatus == UserStatus.LOCKED) {
            return new User(user.getPhone(), query, false, true, true, false, new ArrayList<>());
        } else if (userStatus == UserStatus.DISABLED) {
            return new User(user.getPhone(), query, false, false, true, true, new ArrayList<>());
        } else {
            throw new SystemInnerException();
        }
    }

}
