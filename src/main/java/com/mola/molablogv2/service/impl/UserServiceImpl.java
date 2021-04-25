package com.mola.molablogv2.service.impl;

import com.mola.molablogv2.dto.UserDTO;
import com.mola.molablogv2.emun.UserErrorEmun;
import com.mola.molablogv2.exception.UserException;
import com.mola.molablogv2.pojo.User;
import com.mola.molablogv2.pojo.example.UserExample;
import com.mola.molablogv2.repository.UserMapper;
import com.mola.molablogv2.service.UserService;
import com.mola.molablogv2.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: molamola
 * @Date: 19-6-20 下午4:10
 * @Version 1.0
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper mapper;


    @Override
    public UserDTO findUserByName(String username) {

        //根据name查找user
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> userList = mapper.selectByExample(example);

        UserDTO result = new UserDTO();

        if (userList.size() == 0){
            log.error("不存在该用户!");
            throw new UserException(UserErrorEmun.USER_NOT_EXIST);
        }
        else if (userList.size() > 1){
            log.error("存在多个同名用户!");
            throw new UserException(UserErrorEmun.MUTI_USERS_EXIST);
        }
        else {
            //转化成userDTO,发送到controller
            BeanUtils.copyProperties(userList.get(0), result);
        }
        return result;
    }

    @Override
    public void changePassword(Integer userId, String passwordPre, String passwordNow) {
        User user = mapper.selectByPrimaryKey(userId);
        if (null == user) {
            log.error("不存在该用户!");
            throw new UserException(UserErrorEmun.USER_NOT_EXIST);
        }
        // 1、原密码验证
        if (!MD5Utils.Md5Encode(passwordPre)
                .equalsIgnoreCase(user.getPassword())){
            log.error("原用户名密码错误!");
            throw new UserException(UserErrorEmun.PRE_PASSWORD_ERROR);
        }

        // 2、修改密码
        user.setPassword(MD5Utils.Md5Encode(passwordNow));
        mapper.updateByPrimaryKey(user);
    }
}
