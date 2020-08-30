package com.mola.molablogv2.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: molamola
 * @Date: 19-7-31 上午9:57
 * @Version 1.0
 */
@Data
@Component
public class EmailConfig {

    /**
     * 邮箱地址
     */
    @Value("${emailConf.mailHost}")
    private String mailHost;

    /**
     * 协议
     */
    @Value("${emailConf.mailTransportProtocol}")
    private String mailTransportProtocol;

    /**
     * 是否可信
     */
    @Value("${emailConf.mailSmtpAuth}")
    private String mailSmtpAuth;

    /**
     * 用户名
     */
    @Value("${emailConf.userName}")
    private String userName;

    /**
     * 密码
     */
    @Value("${emailConf.password}")
    private String password;

    /**
     * 端口
     */
    @Value("${emailConf.port}")
    private Integer port;
}
