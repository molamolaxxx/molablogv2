package com.mola.molablogv2.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: molamola
 * @Date: 19-6-20 上午11:08
 * @Version 1.0
 * 登录验证表单
 */
@Data
public class LoginForm {

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名必填")
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "密码必填")
    private String password;

    /**
     * 验证码
     */
    @NotEmpty(message = "验证码必填")
    private String checkcode;
}
