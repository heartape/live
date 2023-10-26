package com.heartape.live.ums.controller;

import com.heartape.live.ums.user.*;
import com.heartape.result.Result;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class UserRegisterController {

    private final VerificationCodeFactory verificationCodeFactory;

    private final VerificationCodeManager verificationCodeManager;

    private final UserRepository userRepository;

    public UserRegisterController(VerificationCodeFactory imageVerificationCodeFactory, VerificationCodeManager verificationCodeManager, UserRepository userRepository) {
        this.verificationCodeFactory = imageVerificationCodeFactory;
        this.verificationCodeManager = verificationCodeManager;
        this.userRepository = userRepository;
    }

    @GetMapping("/code")
    public ImageVerificationCode getCode() {
        ImageVerificationCode imageVerificationCode = (ImageVerificationCode) verificationCodeFactory.next();
        verificationCodeManager.save(imageVerificationCode);
        return imageVerificationCode;
    }

    @PostMapping("/code")
    public Result<Boolean> checkCode(@RequestParam String id, @RequestBody String text) {
        boolean check = verificationCodeManager.check(id, text);
        return Result.success(check);
    }

    @PostMapping
    public Result<Void> register(@RequestBody RegisterForm registerForm) {
        boolean check = verificationCodeManager.check(registerForm.getId(), registerForm.getCode());
        if (!check){
            throw new IllegalArgumentException("验证码错误");
        }
        User user = registerForm.toUser();
        String password = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassword());
        user.setPassword(password);
        userRepository.save(user);
        return Result.success();
    }

    @PostMapping("/phone")
    public Result<Void> registerByPhone(@RequestBody PhoneRegisterForm phoneRegisterForm) {
        boolean check = verificationCodeManager.check(phoneRegisterForm.getId(), phoneRegisterForm.getCode());
        if (!check){
            throw new IllegalArgumentException("验证码错误");
        }
        userRepository.save(phoneRegisterForm.toUser());
        return Result.success();
    }

}
