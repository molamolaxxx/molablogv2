package com.mola.molablogv2.exception;

import com.mola.molablogv2.emun.EmailSenderEmun;

/**
 * @Author: molamola
 * @Date: 19-7-31 上午11:01
 * @Version 1.0
 */
public class EmailSenderException extends RuntimeException{

    //错误码
    Integer code ;

    public EmailSenderException(EmailSenderEmun result){
        super(result.getMsg());
        this.code = result.getCode();
    }

    public EmailSenderException(EmailSenderEmun result, String message){
        super(message);
        this.code = result.getCode();
    }
}
