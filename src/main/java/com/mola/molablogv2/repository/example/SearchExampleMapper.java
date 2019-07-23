package com.mola.molablogv2.repository.example;

import com.mola.molablogv2.pojo.Blog;
import com.mola.molablogv2.pojo.example.BlogExample;
import com.mola.molablogv2.repository.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mola.molablogv2.constant.SearchExampleConst;

import java.util.List;

/**
 * @Author: molamola
 * @Date: 19-7-10 上午11:34
 * @Version 1.0
 * 查找博客
 */
@Component
public class SearchExampleMapper {


    @Autowired
    private BlogMapper mapper;

    public List<Blog> search(Integer kind,String keyword,Integer userId){

        keyword = "%"+keyword+"%";
        BlogExample example = new BlogExample();
        BlogExample.Criteria criteria = example.createCriteria();

        switch (kind){
            case SearchExampleConst.BY_TITLE: {
                criteria.andTitleLike(keyword).andUserIdEqualTo(userId);
                return mapper.selectByExample(example);
            }
            case SearchExampleConst.BY_CONTENT :{
                criteria.andTextLike(keyword).andUserIdEqualTo(userId);
                return mapper.selectByExample(example);
            }
            case SearchExampleConst.BY_TYPE :{
                return mapper.searchByType(userId,keyword);
            }
        }
        return null;
    }

}
