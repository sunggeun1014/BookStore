package com.ezen.bookstore.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {
    @GetMapping("/sample")
    public String sample() {
        return "/user/main/sample";
    }

}
