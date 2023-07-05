package com.cos.security1.controller;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // View를 리턴하겠다.
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
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
    @GetMapping("/loginForm")
    public String loginForm() {
        return "/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "/joinForm";
    }
    @PostMapping("/join")
    public @ResponseBody String join(User user) {
        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        System.out.println(user);
        userRepository.save(user); // 회원가입 잘됨.
        return "redirect:/loginForm";
    }

}
