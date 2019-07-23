package com.mola.molablogv2.service.impl;

import com.mola.molablogv2.dto.ManageDTO;
import com.mola.molablogv2.pojo.BlogComment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: molamola
 * @Date: 19-7-9 下午5:23
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BlogCommentServiceImplTest {

    @Autowired
    BlogCommentServiceImpl blogCommentService;

    @Test
    public void selectAllByBlogId() {
        ManageDTO dto =  blogCommentService.list(55,1,10);
        Assert.assertNotNull(dto);
    }

    @Test
    public void insert(){
        BlogComment blogComment = new BlogComment();
        blogComment.setContent("测试");
        blogComment.setIp("127.0.0.1");
        blogComment.setBlogId(53);
        blogComment.setName("hhhh");
        blogCommentService.insert(blogComment);
    }
}