package com.mola.molablogv2.service;

import com.mola.molablogv2.dto.UserDTO;

/**
 * @Author: molamola
 * @Date: 19-6-20 下午4:02
 * @Version 1.0
 * 用户登录业务
 */
public interface UserService {

    /**
     * 查询一个用户
     * @param username
     * @return
     */
    UserDTO findUserByName(String username);
}
