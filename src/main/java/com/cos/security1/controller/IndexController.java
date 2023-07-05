package com.cos.security1.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // View를 리턴하겠다.
public class IndexController {

    // lcoalhost:8080
    // localhost:8080
    @GetMapping({"","/"})
    public String index() {
        // 머스테치 기본경로 src/main/resources/
        // View Resolver : templates(prefix), .mustache(seffix) 생략가능.
        return "/index";
    }

    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "/admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "/manager";
    }

    // 스프링 시큐리티 해당주소를 가져감.
    @GetMapping("/login")
    public String login() {
        return "/loginForm";
    }

    @GetMapping("/join")
    public @ResponseBody String join() {
        return "/join";
    }

    @GetMapping("/joinProc")
    public @ResponseBody String joinProc() {
        return "회원가입 완료됨.";
    }

}
