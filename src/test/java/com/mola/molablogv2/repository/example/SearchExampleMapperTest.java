package com.mola.molablogv2.repository.example;

import com.mola.molablogv2.constant.SearchExampleConst;
import com.mola.molablogv2.pojo.Blog;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: molamola
 * @Date: 19-7-10 下午1:04
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SearchExampleMapperTest {

    @Autowired
    private SearchExampleMapper mapper;

    @Test
    public void search() {
        List<Blog> blogList = mapper.search(SearchExampleConst.BY_TYPE,"python",0);
        Assert.assertNotNull(blogList);
    }
}