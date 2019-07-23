package com.mola.molablogv2.repository;

import com.mola.molablogv2.pojo.Blog;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: molamola
 * @Date: 19-6-9 下午2:38
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class BlogMapperTest {

    @Autowired
    BlogMapper mapper;

    @Test
    public void deleteByPrimaryKey() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void insertSelective() {
    }

    @Test
    public void selectByPrimaryKey() {
    }

    @Test
    public void updateByPrimaryKeySelective() {

        Blog blog = mapper.selectByPrimaryKey(1);
        blog.setClassId(13);

        mapper.updateByPrimaryKeySelective(blog);
    }

    @Test
    public void updateByPrimaryKey() {
    }

    @Test
    public void preNext(){
        Blog blog = mapper.next(58);
        Assert.assertNotNull(blog);

    }
}