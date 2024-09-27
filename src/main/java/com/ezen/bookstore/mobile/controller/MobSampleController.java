package com.ezen.bookstore.mobile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MobSampleController {
    @GetMapping("/sample2")
    public String sample2() {
        return "/mobile/sample/sample";
    }
}
