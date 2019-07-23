package com.mola.molablogv2.emun;

import lombok.Getter;

/**
 * @Author: molamola
 * @Date: 19-7-11 上午11:00
 * @Version 1.0
 */
@Getter
public enum  ClientErrorEmun {

    SELECT_ALL_ERROR(71,"客户端查找博客列表出错"),
    SELECT_ONE_ERROR(72,"客户端查找博客出错"),
    NO_BLOG_EXIST(73,"找不到博客"),
    NO_TYPE_EXIST(74,"无分类名对应"),
    PRE_NEXT_FIND_ERROR(75,"查找前后篇出错"),
    ADD_PV_ERROR(76,"增加pv失败"),
    LIST_COMMENT_ERROR(77,"加载评论失败"),
    TOKEN_ERROR(78,"token出错"),
    INSERT_COMMENT_ERROR(79,"插入评论失败")
    ;
    private Integer code;

    private String msg;

    ClientErrorEmun(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
