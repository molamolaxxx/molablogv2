package com.mola.molablogv2.emun;

import lombok.Getter;

/**
 * @Author: molamola
 * @Date: 19-7-9 下午4:58
 * @Version 1.0
 */
@Getter
public enum CommentErrorEmun {

    COMMENT_SELECT_ALL_ERROR(51,"评论全部查询错误"),
    COMMENT_DELETE_ERROR(51,"评论删除错误"),
    COMMENT_INSERT_ERROR(51,"评论插入错误");

    private Integer code;

    private String msg;

    CommentErrorEmun(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
