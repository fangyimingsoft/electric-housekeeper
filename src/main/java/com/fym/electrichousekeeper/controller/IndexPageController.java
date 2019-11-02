package com.fym.electrichousekeeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/")
@Controller
public class IndexPageController {

    @RequestMapping
    public String index(){
        return "index";
    }
}
