package com.mola.molablogv2.controller.client;

import com.mola.molablogv2.service.client.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : molamola
 * @Project: molablogv2
 * @Description: 用于提供各种api
 * @date : 2020-11-06 10:40
 **/
@RestController
@RequestMapping("/client")
@Slf4j
public class ApiController {

    @Resource
    private ApiService apiService;

    @GetMapping("/live2d/**")
    public void getLive2dApi(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getServletPath();
        if (!StringUtils.isEmpty(request.getQueryString())) {
            url = request.getServletPath() + "?" + request.getQueryString();
        }
        String suffixUrl = url.substring("/client".length());

        apiService.parseLive2dRequest(suffixUrl, response);
    }
}
