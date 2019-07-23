package com.mola.molablogv2.exception;

import com.mola.molablogv2.emun.CommentErrorEmun;
import com.mola.molablogv2.emun.TypeErrorEmun;
import lombok.Data;

/**
 * @Author: molamola
 * @Date: 19-7-9 下午4:56
 * @Version 1.0
 */
@Data
public class CommentException extends RuntimeException{

    //错误码
    Integer code ;

    public CommentException(CommentErrorEmun result){
        super(result.getMsg());
        this.code = result.getCode();
    }

    public CommentException(CommentErrorEmun result, String message){
        super(message);
        this.code = result.getCode();
    }
}
