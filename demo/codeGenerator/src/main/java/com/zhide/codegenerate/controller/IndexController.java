package com.zhide.codegenerate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("")
public class IndexController {

    @RequestMapping("/")
    public String hello(){
        return "forward:index.html";
    }
}
