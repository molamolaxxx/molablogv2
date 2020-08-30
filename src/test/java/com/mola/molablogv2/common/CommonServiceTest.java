package com.mola.molablogv2.common;

import com.mola.molablogv2.dto.CommentDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: molamola
 * @Date: 19-7-31 下午7:49
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CommonServiceTest {

    @Autowired
    private CommonService service;

    @Test
    public void send() {
        CommentDTO dto = new CommentDTO();
        dto.setBlogId(65);
        dto.setContent("呵呵哈哈哈");
        dto.setName("mola");
        service.send(dto,"测试");
    }
}