package com.mola.molablogv2.exception;

import com.mola.molablogv2.emun.TypeErrorEmun;
import com.mola.molablogv2.emun.UserErrorEmun;
import lombok.Data;

/**
 * @Author: molamola
 * @Date: 19-7-2 下午2:56
 * @Version 1.0
 */
@Data
public class TypeException extends RuntimeException{

    //错误码
    Integer code ;

    public TypeException(TypeErrorEmun result){
        super(result.getMsg());
        this.code = result.getCode();
    }

    public TypeException(TypeErrorEmun result, String message){
        super(message);
        this.code = result.getCode();
    }
}
