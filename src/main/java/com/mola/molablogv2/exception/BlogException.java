package com.mola.molablogv2.exception;

import com.mola.molablogv2.emun.BlogErrorEmun;
import com.mola.molablogv2.emun.UserErrorEmun;
import lombok.Data;

/**
 * @Author: molamola
 * @Date: 19-6-29 下午4:45
 * @Version 1.0
 */
@Data
public class BlogException extends RuntimeException{

    //错误码
    Integer code ;

    public BlogException(BlogErrorEmun result){
        super(result.getMsg());
        this.code = result.getCode();
    }

    public BlogException(BlogErrorEmun result, String message){
        super(message);
        this.code = result.getCode();
    }

}
