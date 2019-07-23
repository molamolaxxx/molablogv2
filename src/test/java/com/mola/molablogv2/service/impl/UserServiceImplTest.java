package com.mola.molablogv2.service.impl;

import com.mola.molablogv2.dto.UserDTO;
import com.mola.molablogv2.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author: molamola
 * @Date: 19-6-20 下午8:34
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    public void findUserByName() {
        UserDTO dto = userService.findUserByName("molax");
        Assert.assertNotNull(dto);
    }
}