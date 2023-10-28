package com.heartape.live.ums.controller;

import com.heartape.live.ums.user.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class UserLoginController {

    private final UserRepository userRepository;

    private final VerificationCodeFactory verificationCodeFactory;

    private final VerificationCodeManager verificationCodeManager;

    public UserLoginController(UserRepository userRepository, VerificationCodeFactory phoneVerificationCodeFactory, VerificationCodeManager verificationCodeManager) {
        this.userRepository = userRepository;
        this.verificationCodeFactory = phoneVerificationCodeFactory;
        this.verificationCodeManager = verificationCodeManager;
    }

    @GetMapping("/username")
    public UserLoginInfo username(@RequestParam String username) {
        QUser qUser = QUser.user;
        Optional<User> optional = userRepository.findOne(qUser.username.eq(username));
        if (optional.isEmpty()) {
            return null;
        }
        User user = optional.get();
        return new UserLoginInfo(user.getUsername(), user.getPassword(), user.getStatus(), new HashSet<>());
    }

    @PostMapping("/phone")
    public VerificationCode createCode(@RequestParam String phone) {
        VerificationCode verificationCode = verificationCodeFactory.next(phone);
        verificationCodeManager.save(verificationCode);
        return verificationCode;
    }

    @GetMapping("/phone")
    public PhoneLoginInfo checkCode(@RequestParam String phone) {
        QUser qUser = QUser.user;
        Optional<User> optional = userRepository.findOne(qUser.phone.eq(phone));
        if (optional.isEmpty()) {
            return null;
        }
        User user = optional.get();
        String code = verificationCodeManager.query(phone);
        if (!StringUtils.hasText(code)) {
            return null;
        }
        return new PhoneLoginInfo(phone, code, user.getStatus(), new HashSet<>());
    }

}
