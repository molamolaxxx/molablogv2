package com.mola.molablogv2.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: molamola
 * @Date: 19-6-27 下午1:51
 * @Version 1.0
 * 如果未登录，无法访问任何接口
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1.判断是否登录
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        //2.未登录，返回到登录界面
        if (null == username){
            response.sendRedirect("/molablog");
            return false;
        }

        //3.已登录。不拦截
        return true;
    }
}
