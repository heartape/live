package com.heartape.live.ums.controller;

import com.heartape.exception.SystemInnerException;
import com.heartape.live.ums.user.*;
import com.heartape.result.Result;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/register")
public class UserRegisterController {

    private final VerificationCodeFactory verificationCodeFactory;

    private final VerificationCodeManager verificationCodeManager;

    private final UserRepository userRepository;

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
        userRepository.save(registerForm.toUser());
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
