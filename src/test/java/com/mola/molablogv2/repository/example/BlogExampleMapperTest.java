package com.mola.molablogv2.repository.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author: molamola
 * @Date: 19-7-5 上午10:20
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BlogExampleMapperTest {
    @Autowired
    BlogExampleMapper exampleMapper;

    @Test
    public void selectAllBlogByUserId() {
    }

    @Test
    public void deleteBlogByClassId() {
        Integer result = exampleMapper.deleteBlogByClassId(6);
        return;
    }
}