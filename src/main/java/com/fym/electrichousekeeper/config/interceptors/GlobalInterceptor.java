package com.fym.electrichousekeeper.config.interceptors;

import lombok.Setter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Setter
public class GlobalInterceptor implements HandlerInterceptor {

    private List<Integer> noAuthStatus = Arrays.asList(401,404,405);

    private Boolean requireLogin = true;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!requireLogin){
            return true;
        }
        String requestURI = request.getRequestURI();
        if(requestURI.startsWith("/assets") || requestURI.equals("/") || requestURI.startsWith("/login")){
            return true;
        }
        HttpSession session = request.getSession(false);
        int status = response.getStatus();
        if(session != null){
            return true;
        }else if(noAuthStatus.contains(status)){
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
