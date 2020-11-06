package com.mola.molablogv2.utils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.regex.Pattern;

/**
 * @Author: molamola
 * @Date: 19-7-5 下午12:51
 * @Version 1.0
 * 公共验证
 */
public class ValidUtil {

    public static Boolean userValid(HttpServletResponse response , HttpSession session,Integer userId){
        //判断用户是否正确
        if (null == session.getAttribute("userId") || session.getAttribute("userId") != userId) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }
        return true;
    }

    /**
     * 判断路径是否是图片
     * @param path
     * @return
     */
    public static boolean isImagePath(String path) {
        String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.png|.PNG)$";
        return Pattern.compile(reg).matcher(path).find();
    }

    /**
     * 判断是否是数据流
     * @param path
     * @return
     */
    public static boolean isStream(String path) {
        String reg = ".+(.MOC|.moc|.mtn)$";
        return Pattern.compile(reg).matcher(path).find();
    }
}
