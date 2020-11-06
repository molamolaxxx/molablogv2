package com.mola.molablogv2.controller;

import com.mola.molablogv2.common.ServerResponse;
import com.mola.molablogv2.dto.UserDTO;
import com.mola.molablogv2.emun.UserErrorEmun;
import com.mola.molablogv2.exception.UserException;
import com.mola.molablogv2.form.LoginForm;
import com.mola.molablogv2.service.UserService;
import com.mola.molablogv2.utils.MD5Utils;
import com.mola.molablogv2.utils.RandomValidateCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author: molamola
 * @Date: 19-6-20 上午11:02
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @return
     */
    @PostMapping("/login")
    public ServerResponse login(HttpServletRequest request , @Valid LoginForm loginForm,
                                 BindingResult bindingResult , HttpServletResponse response){

        //1.表单非空验证
        if (bindingResult.hasErrors()){
            log.error("验证表单出错:{}",bindingResult.getFieldError().getDefaultMessage());
            return ServerResponse.createByErrorCodeMessage(UserErrorEmun.FORM_VALID_FAILED.getCode()
                    ,bindingResult.getFieldError().getDefaultMessage());
        }

        //2.验证码是否正确
        if (!loginForm.getCheckcode()
                .equalsIgnoreCase((String) request.getSession()
                        .getAttribute(RandomValidateCode.RANDOMCODEKEY))){
            log.error("验证码出错");
            return ServerResponse.createByErrorCodeMessage(UserErrorEmun.VALIDATE_CODE_ERROR.getCode()
                    , UserErrorEmun.VALIDATE_CODE_ERROR.getMsg());
        }

        //3.用户是否存在
        UserDTO userDTO;
        try {
            userDTO = userService.findUserByName(loginForm.getUsername());
        }
        catch (UserException e){
            return ServerResponse.createByErrorCodeMessage(e.getCode(),e.getMessage());
        }

        //4.密码是否正确 加密校验
        if (!MD5Utils.Md5Encode(loginForm.getPassword())
                .equalsIgnoreCase(userDTO.getPassword())){
            log.error("用户名密码错误!");
            return ServerResponse.createByErrorCodeMessage(UserErrorEmun.USER_PASSWORD_ERROR.getCode()
                    , UserErrorEmun.USER_PASSWORD_ERROR.getMsg());
        }

        //5.设置session
        request.getSession().setAttribute("username",loginForm.getUsername());
        request.getSession().setAttribute("userId",userDTO.getId());

        return ServerResponse.createBySuccess();
    }
}
