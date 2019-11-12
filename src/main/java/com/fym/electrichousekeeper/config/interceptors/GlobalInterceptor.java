package com.fym.electrichousekeeper.config.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GlobalInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        boolean auth = false;
        if(uri.startsWith("/api")){
            HttpSession session = request.getSession(false);
            if(session != null){
                auth = true;
            }else{
                response.sendError(403,"沒有权限");
            }
        }else {
            auth = true;
        }
        return auth;
    }
}
