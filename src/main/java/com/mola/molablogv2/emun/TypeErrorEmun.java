package com.mola.molablogv2.emun;

import lombok.Getter;

/**
 * @Author: molamola
 * @Date: 19-7-2 下午2:57
 * @Version 1.0
 * 种类错误枚举类
 */
@Getter
public enum TypeErrorEmun {

    NO_SUCH_TYPE_EXIST(21,"不存在分类"),
    TYPE_INSERT_ERROR(22,"分类查询错误"),
    TYPE_DELETE_ERROR(23,"分类删除失败"),
    TYPE_UPDATE_ERROR(23,"分类删除失败");

    private Integer code;

    private String msg;

    TypeErrorEmun(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
