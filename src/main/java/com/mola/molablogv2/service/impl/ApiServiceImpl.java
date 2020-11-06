package com.mola.molablogv2.service.impl;

import com.mola.molablogv2.service.client.ApiService;
import com.mola.molablogv2.utils.IOUtils;
import com.mola.molablogv2.utils.ValidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author : molamola
 * @Project: molablogv2
 * @Description:
 * @date : 2020-11-06 10:54
 **/
@Service
@Slf4j
public class ApiServiceImpl implements ApiService {

    private static final String LIVE2D_DOMAIN = "https://api3.fghrsh.net";

    @Resource
    private RestTemplate restTemplate;

    @Override
    public void parseLive2dRequest(String suffixUrl, HttpServletResponse response) {
        StringBuilder sb = new StringBuilder(LIVE2D_DOMAIN);
        if (!suffixUrl.startsWith("/")) {
            sb.append("/");
        }

        String finalUrl = sb.append(suffixUrl).toString();
        response.setHeader("Connection", "keep-alive");
        response.setHeader("Date", new Date().toString());
        if (ValidUtil.isImagePath(suffixUrl)) {
            parseImageRequest(response, finalUrl);
        } else if (ValidUtil.isStream(suffixUrl)){
            parseStreamRequest(response, finalUrl);
        } else {
            parseTextRequest(response, finalUrl);
        }
    }

    private void parseImageRequest(HttpServletResponse response, String finalUrl) {
        response.setContentType("image/png");
        try {
            BufferedImage input = ImageIO.read(new URL(finalUrl));
            ImageIO.write(input, "png", response.getOutputStream());
        } catch (Exception e) {
            log.error("图片访问连接异常", e);
        }
    }

    private void parseTextRequest(HttpServletResponse response, String finalUrl){
        response.setContentType("application/json");
        IOUtils.writeString2OutputStream(
                fillHeaderAndGetResult(response, finalUrl),
                response);
    }

    private void parseStreamRequest(HttpServletResponse response, String finalUrl){
        response.setContentType("application/octet-stream");
        IOUtils.writeString2OutputStream(
                fillHeaderAndGetResult(response, finalUrl),
                response);
    }

    private String fillHeaderAndGetResult(HttpServletResponse response, String finalUrl) {
        ResponseEntity<String> entity = restTemplate.getForEntity(finalUrl, String.class);
        for (Map.Entry<String, List<String>> entry : entity.getHeaders().entrySet()) {
            for (String value : entry.getValue()) {
                response.setHeader(entry.getKey(), value);
            }
        }
        String result = null;
        if (StringUtils.isEmpty(entity.getBody())) {
            result = "none";
        } else {
            result = entity.getBody();
        }
        return result;
    }
}
