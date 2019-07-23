package com.mola.molablogv2.dto;

import lombok.Data;

/**
 * @Author: molamola
 * @Date: 19-6-20 下午4:09
 * @Version 1.0
 */
@Data
public class UserDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
