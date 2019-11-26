package com.fym.electrichousekeeper.config.interceptors;

import lombok.Setter;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Setter
public class GlobalInterceptor implements HandlerInterceptor {

    private List<Integer> noAuthStatus = Arrays.asList(401,404,405);

    private List<String> authUri = Arrays.asList("/index","/management","/api/");

    private Boolean requireLogin = true;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!requireLogin){
            return  true;
        }
        String uri = request.getRequestURI();
        long count = authUri.stream().filter(item -> uri.startsWith(item)).count();
        if(count > 0){
            HttpSession session = request.getSession(false);
            if(session == null){
                response.sendRedirect("/login");
                return false;
            }else{
                return true;
            }
        }else{
            if(noAuthStatus.contains(response.getStatus())){
                response.sendRedirect("/");
                return false;
            }
            return  true;
        }
    }
}
