package com.mola.molablogv2.service.client;

import javax.servlet.http.HttpServletResponse;

/**
 * @author : molamola
 * @Project: molablogv2
 * @Description:
 * @date : 2020-11-06 10:48
 **/
public interface ApiService {

    /**
     * 转换live2d的请求
     * @param suffixUrl
     * @return
     */
    void parseLive2dRequest(String suffixUrl, HttpServletResponse response);
}
