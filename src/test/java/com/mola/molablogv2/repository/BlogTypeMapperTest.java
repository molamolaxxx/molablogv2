package com.mola.molablogv2.repository;

import com.mola.molablogv2.pojo.BlogType;
import com.mola.molablogv2.pojo.example.BlogTypeExample;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: molamola
 * @Date: 19-6-27 下午2:48
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class BlogTypeMapperTest {

    @Autowired
    BlogTypeMapper mapper;

    @Test
    public void selectByExample() {
        BlogTypeExample example = new BlogTypeExample();
        BlogTypeExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(0);

        List<BlogType> blogTypeList = mapper.selectByExample(example);
        Assert.assertNotEquals(0,blogTypeList.size());
    }

    @Test
    public void insert(){
        BlogType type = new BlogType();
        type.setClassName("233");
        type.setParentId(2);
        type.setUserId(0);

        mapper.insert(type);
    }
}