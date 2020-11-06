package com.mola.molablogv2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author : molamola
 * @Project: molablogv2
 * @Description:
 * @date : 2020-11-06 10:53
 **/
@Configuration
public class HttpConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
}
