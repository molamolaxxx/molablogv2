package com.mola.molablogv2.emun;

import lombok.Getter;

/**
 * @Author: molamola
 * @Date: 19-7-2 下午8:39
 * @Version 1.0
 * 项目中的共同错误
 */
@Getter
public enum CommonErrorEmun {

    JSON_PARSE_ERROR(101,"json转换错误"),
    JSON_PARSE_EMPTY(102,"json转换后对象为空"),
    STITCHING_ERROR(16,"数据拼接错误")
    ;

    private Integer code;

    private String msg;

    CommonErrorEmun(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
