package com.learncollab.softalk.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home";
    }

    /*로그인 화면*/
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /*회원가입 화면*/
    @GetMapping("/join")
    public String joinPage() {
        return "join";
    }
}
