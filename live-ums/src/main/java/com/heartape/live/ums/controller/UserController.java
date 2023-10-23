package com.heartape.live.ums.controller;

import com.heartape.exception.SystemInnerException;
import com.heartape.live.ums.user.ImageVerificationCode;
import com.heartape.live.ums.user.PhoneRegisterForm;
import com.heartape.live.ums.user.RegisterForm;
import com.heartape.live.ums.user.VerificationCodeFactory;
import com.heartape.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/register")
public class UserController {

    private final VerificationCodeFactory verificationCodeFactory;

    @GetMapping("/code")
    public void getCode(HttpServletResponse response) {
        ImageVerificationCode imageVerificationCode = (ImageVerificationCode) verificationCodeFactory.next();
        // 设置响应头，防止缓存
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        try {
            ImageIO.write(imageVerificationCode.getImage(), "jpg", response.getOutputStream());
        } catch (IOException e) {
            throw new SystemInnerException();
        }
    }

    @PostMapping
    public Result<?> register(@RequestBody RegisterForm registerForm) {
        return Result.success();
    }

    @PostMapping("/phone")
    public Result<?> registerByPhone(@RequestBody PhoneRegisterForm phoneRegisterForm) {
        return Result.success();
    }

}
