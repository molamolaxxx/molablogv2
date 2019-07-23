package com.mola.molablogv2.utils;

/**
 * @Author: molamola
 * @Date: 19-7-22 下午5:44
 * @Version 1.0
 * token工具类
 */
public class TokenUtils {

    private static final String SALT = "molamolaxxx";

    public static String getToken(String raw){
        //1.拼接加盐
        String before = raw + SALT;

        //2.获取哈希值
        Integer hash = (Integer) before.hashCode();

        //3.再次拼接
        String after = hash + SALT;

        return MD5Utils.Md5Encode(after);
    }
    public static void main(String[] args){
        System.out.println(getToken("127.0.1.1"));
    }
}
