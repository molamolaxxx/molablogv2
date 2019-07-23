package com.mola.molablogv2.utils;

import org.springframework.util.DigestUtils;

/**
 * @Author: molamola
 * @Date: 19-6-21 下午1:46
 * @Version 1.0
 */
public class MD5Utils {

    /**
     * md5加密
     * @param origin
     * @return
     */
    public static String Md5Encode(String origin){

        return DigestUtils.md5DigestAsHex(origin.getBytes());
    }

}
