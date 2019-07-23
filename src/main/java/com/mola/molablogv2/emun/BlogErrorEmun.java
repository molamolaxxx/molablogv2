package com.mola.molablogv2.emun;

import lombok.Getter;

/**
 * @Author: molamola
 * @Date: 19-6-29 下午4:42
 * @Version 1.0
 * blog相关异常信息
 */
@Getter
public enum BlogErrorEmun {

    BLOG_SELECT_ALL_ERROR(11,"博客查找异常"),
    NO_BLOG_EXIST(12,"博客不存在"),
    BLOG_UPDATE_ERROR(13,"博客更新错误"),
    BLOG_DELETE_ERROR(14,"博客删除错误"),
    BLOG_INSERT_ERROR(14,"博客插入错误"),
    BLOG_SEARCH_ERROR(15,"博客查询错误"),
    ;

    private Integer code;

    private String msg;

    BlogErrorEmun(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
