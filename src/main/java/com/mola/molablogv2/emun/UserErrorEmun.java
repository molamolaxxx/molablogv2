package com.mola.molablogv2.emun;

import lombok.Getter;

/**
 * @Author: molamola
 * @Date: 19-6-10 下午5:18
 * @Version 1.0
 * 错误枚举类
 */
@Getter
public enum UserErrorEmun {

    //登录部分
    FORM_VALID_FAILED(1,"登录表单验证出错"),
    GET_VALIDATE_CODE_ERROR(2,"获取验证码错误"),
    VALIDATE_CODE_ERROR(3,"验证码错误"),
    USER_NOT_EXIST(4,"用户不存在"),
    MUTI_USERS_EXIST(5,"存在多个同名用户"),
    USER_PASSWORD_ERROR(6,"用户名密码错误"),
    ;

    private Integer code;

    private String msg;

    UserErrorEmun(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
