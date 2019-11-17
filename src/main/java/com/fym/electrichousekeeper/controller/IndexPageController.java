package com.fym.electrichousekeeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

@RequestMapping("/")
@Controller
public class IndexPageController {

    @RequestMapping
    public String login(){
        return "login";
    }

    @RequestMapping("/index")
    public String index(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            return "index";
        }
        return "login";
    }

    @RequestMapping("management")
    public String management(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            return "management";
        }
        return "login";
    }
}
