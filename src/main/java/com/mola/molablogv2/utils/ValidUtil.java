package com.mola.molablogv2.utils;

import com.mola.molablogv2.common.ServerResponse;
import com.mola.molablogv2.emun.SessionErrorEmun;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: molamola
 * @Date: 19-7-5 下午12:51
 * @Version 1.0
 * 公共验证
 */
public class ValidUtil {

    public static Boolean userValid(HttpServletResponse response , HttpSession session,Integer userId){
        //判断用户是否正确
        if (null == session.getAttribute("userId") || session.getAttribute("userId") != userId) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }
        return true;
    }
}
