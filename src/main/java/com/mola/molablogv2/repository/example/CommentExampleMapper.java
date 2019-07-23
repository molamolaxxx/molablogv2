package com.mola.molablogv2.repository.example;

import com.mola.molablogv2.pojo.BlogComment;

import com.mola.molablogv2.pojo.example.BlogCommentExample;
import com.mola.molablogv2.repository.BlogCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: molamola
 * @Date: 19-7-8 下午4:16
 * @Version 1.0
 */
@Component
public class CommentExampleMapper {

    @Autowired
    BlogCommentMapper mapper;

    public List<BlogComment> selectAllByBlogId(Integer blogId){
        BlogCommentExample example = new BlogCommentExample();
        BlogCommentExample.Criteria criteria = example.createCriteria();
        criteria.andBlogIdEqualTo(blogId);
        return mapper.selectByExample(example);
    }

}
