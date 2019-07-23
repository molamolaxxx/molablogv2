package com.mola.molablogv2.repository.example;

import com.mola.molablogv2.emun.PublishStatus;
import com.mola.molablogv2.pojo.Blog;
import com.mola.molablogv2.pojo.example.BlogExample;
import com.mola.molablogv2.repository.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: molamola
 * @Date: 19-7-1 下午8:08
 * @Version 1.0
 * BlogMapper对于逆向工程的扩展
 */
@Component
public class BlogExampleMapper {

    @Autowired
    private BlogMapper mapper;

    /**
     * 根据id返回所有博客
     * @param userId
     * @return
     */
    public List<Blog> selectAllBlogByUserId(Integer userId){
        BlogExample blogExample = new BlogExample();
        BlogExample.Criteria blogCriteria = blogExample.createCriteria();
        blogCriteria.andUserIdEqualTo(userId);
        return mapper.selectByExample(blogExample);
    }

    /**
     * 根据id返回所有发表博客
     * @param userId
     * @return
     */
    public List<Blog> selectAllPublishedBlogByUserId(Integer userId){
        BlogExample blogExample = new BlogExample();
        BlogExample.Criteria blogCriteria = blogExample.createCriteria();
        blogCriteria.andUserIdEqualTo(userId);
        blogCriteria.andPublishedEqualTo(PublishStatus.PUBLISHED.getCode());
        return mapper.selectByExample(blogExample);
    }

    /**
     * 根据id返回所有发表博客
     * @param blogId
     * @return
     */
    public List<Blog> selectOnePublished(Integer blogId){
        BlogExample blogExample = new BlogExample();
        BlogExample.Criteria blogCriteria = blogExample.createCriteria();
        blogCriteria.andIdEqualTo(blogId);
        blogCriteria.andPublishedEqualTo(PublishStatus.PUBLISHED.getCode());
        return mapper.selectByExample(blogExample);
    }

    /**
     * 根据id返回所有发表博客 ,按照更新时间排序
     * @param userId
     * @return
     */
    public List<Blog> selectAllDescByUpdateTime(Integer userId){
        BlogExample blogExample = new BlogExample();
        blogExample.setOrderByClause("blog.update_time DESC");
        BlogExample.Criteria blogCriteria = blogExample.createCriteria();
        blogCriteria.andUserIdEqualTo(userId);
        blogCriteria.andPublishedEqualTo(PublishStatus.PUBLISHED.getCode());

        return mapper.selectByExample(blogExample);
    }


    /**
     * 根据分类id删除blog
     * @param classId
     * @return
     */
    public Integer deleteBlogByClassId(Integer classId){
        BlogExample blogExample = new BlogExample();
        BlogExample.Criteria blogCriteria = blogExample.createCriteria();
        blogCriteria.andClassIdEqualTo(classId);
        return mapper.deleteByExample(blogExample);
    }
}
