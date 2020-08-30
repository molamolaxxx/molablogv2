package com.mola.molablogv2.annotation;

import java.lang.annotation.*;

/**
 * @Author: molamola
 * @Date: 19-7-31 上午9:25
 * @Version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SendEmail {

    /**
     * 收件人地址
     */
    String responseAddress() default "";

    /**
     * 附件地址
     * @return
     */
    String attachFilePath() default "";

}
