package com.mola.molablogv2.emun;

import lombok.Getter;

/**
 * @Author: molamola
 * @Date: 19-6-21 下午2:13
 * @Version 1.0
 */
@Getter
public enum SessionErrorEmun {

    //session相关
    NO_USERNAME_EXIST(31,"session中用户名不存在"),

    CLEAR_SESSION_ERROR(32,"清空session出错"),

    USERID_ERROR(33,"用户信息不正确"),

    TYPE_INFO_EMPTY(34,"分类详细信息缺失")
    ;

    private Integer code;

    private String msg;

    SessionErrorEmun(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
