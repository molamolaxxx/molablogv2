package com.mola.molablogv2.exception;

import com.mola.molablogv2.emun.CommonErrorEmun;
import com.mola.molablogv2.emun.TypeErrorEmun;
import lombok.Data;

/**
 * @Author: molamola
 * @Date: 19-7-2 下午8:40
 * @Version 1.0
 */
@Data
public class CommonException extends RuntimeException{

    //错误码
    Integer code ;

    public CommonException(CommonErrorEmun result){
        super(result.getMsg());
        this.code = result.getCode();
    }

    public CommonException(CommonErrorEmun result, String message){
        super(message);
        this.code = result.getCode();
    }
}
