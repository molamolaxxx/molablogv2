package com.mola.molablogv2.exception;

import com.mola.molablogv2.emun.UserErrorEmun;
import lombok.Data;

/**
 * @Author: molamola
 * @Date: 19-6-11 下午12:24
 * @Version 1.0
 * 博客运行时异常
 */
@Data
public class UserException extends RuntimeException {

    //错误码
    Integer code ;

    public UserException(UserErrorEmun result){
        super(result.getMsg());
        this.code = result.getCode();
    }

    public UserException(UserErrorEmun result, String message){
        super(message);
        this.code = result.getCode();
    }

}
