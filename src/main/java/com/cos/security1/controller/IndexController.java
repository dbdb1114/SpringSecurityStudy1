package com.cos.security1.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

}
