package com.heartape.live.im.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    /**
     * 请求im cookie
     */
    @GetMapping("/cookie")
    public void cookie(HttpSession session, Authentication authentication){
        System.out.println(authentication.getName());
        session.setAttribute("LIVE_" + HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, authentication);
    }

}
