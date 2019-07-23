package com.mola.molablogv2.service.impl;

import com.mola.molablogv2.dto.BlogTypeDTO;
import com.mola.molablogv2.service.BlogTypeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: molamola
 * @Date: 19-6-27 下午4:22
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BlogTypeServiceImplTest {

    @Autowired
    BlogTypeService service;

    @Test
    public void selectAllTypesByUserId() {
        BlogTypeDTO blogTypeDTO = service.selectAllByUserId(0);
        Assert.assertNotEquals(0,blogTypeDTO.getChildren().size());
    }

    @Test
    public void selectOneById(){
        BlogTypeDTO blogTypeDTO = service.selectOneById(38);
        Assert.assertNotNull(blogTypeDTO);
    }

    @Test
    public void update(){
        BlogTypeDTO dto = new BlogTypeDTO();
        dto.setId(29);
        dto.setClassName("阿里云维护");
        Integer result = service.update(dto);
        Assert.assertEquals(true,result == 1);
    }

    @Test
    public void insert(){
        BlogTypeDTO dto = new BlogTypeDTO();
        dto.setParentId(2);
        dto.setClassName("233");
        dto.setUserId(0);

        Integer result = service.insert(dto);
        Assert.assertEquals(true,result == 1);
    }

    @Test
    public void delete(){
        BlogTypeDTO dto = new BlogTypeDTO();
        dto.setId(44);
        dto.setUserId(0);

        Integer result = service.delete(dto);
        Assert.assertEquals(true,result == 1);
    }
}