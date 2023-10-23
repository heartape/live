package com.heartape.live.ums.controller;

import com.heartape.exception.SystemInnerException;
import com.heartape.live.ums.user.*;
import com.heartape.result.Result;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/register")
public class UserRegisterController {

    private final VerificationCodeFactory verificationCodeFactory;

    private final VerificationCodeManager verificationCodeManager;

    @GetMapping("/code")
    public void getCode(HttpServletResponse response) {
        ImageVerificationCode imageVerificationCode = (ImageVerificationCode) verificationCodeFactory.next();
        verificationCodeManager.save(imageVerificationCode);
        // 设置响应头，防止缓存
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        try {
            ImageIO.write(imageVerificationCode.getImage(), "jpg", response.getOutputStream());
        } catch (IOException e) {
            throw new SystemInnerException();
        }
    }

    @PostMapping("/code")
    public Result<Boolean> checkCode(@RequestBody String text) {
        boolean check = verificationCodeManager.check(text);
        return Result.success(check);
    }

    @PostMapping
    public Result<Boolean> register(@RequestBody RegisterForm registerForm) {
        boolean check = verificationCodeManager.check(registerForm.getCode());
        if (check){

        }
        return Result.success(check);
    }

    @PostMapping("/phone")
    public Result<?> registerByPhone(@RequestBody PhoneRegisterForm phoneRegisterForm) {
        return Result.success();
    }

}
