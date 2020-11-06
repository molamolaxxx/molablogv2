package com.mola.molablogv2.controller;

import com.mola.molablogv2.common.ServerResponse;
import com.mola.molablogv2.emun.SessionErrorEmun;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: molamola
 * @Date: 19-6-21 下午12:46
 * @Version 1.0
 * 管理SESSION的controller
 */
@RestController
@Slf4j
@RequestMapping("/session")
public class SessionController {

    /**
     * 获取session中的user
     * @param request
     * @return
     */
    @GetMapping("/user")
    public ServerResponse user(HttpServletRequest request){

        String username = (String) request.getSession().getAttribute("username");
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        if (null == username){
            log.info("session不存在");
            return ServerResponse.createByErrorCodeMessage(SessionErrorEmun.NO_USERNAME_EXIST.getCode()
                    , SessionErrorEmun.NO_USERNAME_EXIST.getMsg());
        }

        Map userMap = new HashMap();
        userMap.put("username",username);
        userMap.put("userId",userId);

        return ServerResponse.createBySuccess(userMap);
    }

    @GetMapping("/clear")
    public ServerResponse clear(HttpServletRequest request ,HttpServletResponse response){

        try {
            request.getSession().invalidate();
        }
        catch (Exception e) {
            log.error("清空session出错");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return ServerResponse.createByErrorCodeMessage(SessionErrorEmun.CLEAR_SESSION_ERROR.getCode(),
                    SessionErrorEmun.CLEAR_SESSION_ERROR.getMsg());
        }

        return ServerResponse.createBySuccess();
    }
}
