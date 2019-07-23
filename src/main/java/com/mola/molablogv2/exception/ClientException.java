package com.mola.molablogv2.exception;

import com.mola.molablogv2.emun.BlogErrorEmun;
import com.mola.molablogv2.emun.ClientErrorEmun;
import lombok.Data;

/**
 * @Author: molamola
 * @Date: 19-7-11 上午10:59
 * @Version 1.0
 */
@Data
public class ClientException extends RuntimeException {

    //错误码
    Integer code ;

    public ClientException(ClientErrorEmun result){
        super(result.getMsg());
        this.code = result.getCode();
    }

    public ClientException(ClientErrorEmun result, String message){
        super(message);
        this.code = result.getCode();
    }
}
