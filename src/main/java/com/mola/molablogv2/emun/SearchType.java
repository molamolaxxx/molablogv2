package com.mola.molablogv2.emun;

import lombok.Getter;

/**
 * @Author: molamola
 * @Date: 19-7-10 下午12:37
 * @Version 1.0
 */
@Getter
public enum SearchType {

    BY_TITLE(0,"搜索题目中关键字"),
    BY_CONTENT(1,"搜索内容中关键字"),
    BY_TYPE(2,"搜索分类中关键字");

    private Integer code;

    private String msg;

    SearchType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
