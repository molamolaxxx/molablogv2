package com.mola.molablogv2.controller;

import com.mola.molablogv2.emun.UserErrorEmun;
import com.mola.molablogv2.exception.UserException;
import com.mola.molablogv2.utils.RandomValidateCode;
import com.mola.molablogv2.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: molamola
 * @Date: 19-6-20 上午10:39
 * @Version 1.0
 * 用于验证的controller
 */
@Controller
@RequestMapping("/validate")
@Slf4j
public class ValidateController {

    @GetMapping("/checkcode")
    private void checkCode(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {

        //设置相应类型,告诉浏览器输出的内容为图片
        response.setContentType("image/jpeg");
        //设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);

        RandomValidateCode generator = new RandomValidateCode();
        try {
            generator.getRandcode(request, response);
        }
        catch (Exception e){
            log.error("验证码出错:{}",e);
            throw new UserException(UserErrorEmun.GET_VALIDATE_CODE_ERROR);
        }
    }

    @GetMapping("/token")
    @ResponseBody
    private String token(HttpServletRequest request){
        return TokenUtils.getToken(request.getHeader("User-Agent"));
    }
}
