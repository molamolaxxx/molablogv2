package com.mola.molablogv2.emun;

import lombok.Getter;

/**
 * @Author: molamola
 * @Date: 19-7-31 上午11:00
 * @Version 1.0
 */
@Getter
public enum  EmailSenderEmun {

    RETURN_NON_NULL(141,"@SendEmail返回值不能为空，应该为EmailParser"),
    RETURN_ERROR(142,"@SendEmail返回值错误，应该为EmailParser"),
    MESSAGE_INIT_ERROR(143,"邮件初始化错误"),
    PARSE_NULL_POINT(144,"EmailParser空指针异常"),
    EMAIL_SEND_ERROR(145,"邮件发送失败"),
    THREAD_POOL_INIT_ERROR(146,"线程池初始化失败")
    ;

    private Integer code;

    private String msg;

    EmailSenderEmun(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
