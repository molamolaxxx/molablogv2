package com.mola.molablogv2.service.impl;

import com.mola.molablogv2.dto.BlogDTO;
import com.mola.molablogv2.dto.ManageDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: molamola
 * @Date: 19-7-1 下午4:37
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogServiceImplTest {

    @Autowired
    private BlogServiceImpl blogService;

    @Test
    public void selectAllBlog() {
        blogService.selectAll(0,1,10);
    }

    @Test
    public void update(){
        BlogDTO item = new BlogDTO();
        item.setId(1);
        item.setTitle("欢迎来到molamola的博客");
        Integer result = blogService.update(item);

        Assert.assertEquals(true,result == 1);
    }

    @Test
    public void delete(){
        BlogDTO item = new BlogDTO();
        item.setId(233);

        Integer result = blogService.delete(item);

        Assert.assertEquals(true,result == 1);
    }

    @Test
    public void search(){
        ManageDTO dto = blogService.search("spring",0);
        Assert.assertNotNull(dto);
    }
}