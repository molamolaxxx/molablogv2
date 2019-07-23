package com.mola.molablogv2.repository.example;

import com.mola.molablogv2.pojo.BlogType;

import com.mola.molablogv2.pojo.example.BlogTypeExample;
import com.mola.molablogv2.repository.BlogTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: molamola
 * @Date: 19-7-1 下午8:51
 * @Version 1.0
 * BlogTypeMapper对于逆向工程的扩展
 */
@Component
public class BlogTypeExampleMapper {

    @Autowired
    private BlogTypeMapper mapper;

    public List<BlogType> selectAllTypesByUserId(Integer userId){
        BlogTypeExample example = new BlogTypeExample();
        BlogTypeExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        return mapper.selectByExample(example);
    }
}
