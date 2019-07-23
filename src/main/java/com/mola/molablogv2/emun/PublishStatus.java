package com.mola.molablogv2.emun;

import lombok.Data;
import lombok.Getter;

/**
 * @Author: molamola
 * @Date: 19-6-29 下午4:25
 * @Version 1.0
 * 发表枚举
 */
@Getter
public enum PublishStatus {

    PUBLISHED(0,"已发表"),
    NOT_PUBLISHED(1,"未发表");

    private Integer code;

    private String msg;

    PublishStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
