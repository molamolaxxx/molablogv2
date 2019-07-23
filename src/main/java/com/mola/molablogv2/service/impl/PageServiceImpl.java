package com.mola.molablogv2.service.impl;

import com.mola.molablogv2.dto.CommentDTO;
import com.mola.molablogv2.dto.ManageDTO;
import com.mola.molablogv2.dto.client.ClientDTO;
import com.mola.molablogv2.dto.client.PreNextDTO;
import com.mola.molablogv2.emun.ClientErrorEmun;
import com.mola.molablogv2.exception.ClientException;
import com.mola.molablogv2.exception.CommentException;
import com.mola.molablogv2.pojo.Blog;
import com.mola.molablogv2.pojo.BlogComment;
import com.mola.molablogv2.pojo.BlogType;
import com.mola.molablogv2.repository.BlogMapper;
import com.mola.molablogv2.repository.BlogTypeMapper;
import com.mola.molablogv2.repository.example.BlogExampleMapper;
import com.mola.molablogv2.service.BlogCommentService;
import com.mola.molablogv2.service.client.PageService;
import com.mola.molablogv2.utils.BeanUtilsPlug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: molamola
 * @Date: 19-7-15 上午11:54
 * @Version 1.0
 */
@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogExampleMapper blogExampleMapper;

    @Autowired
    private BlogTypeMapper blogTypeMapper;

    @Autowired
    private BlogCommentService commentService;

    @Override
    public ClientDTO selectOne(Integer blogId) {
        List<Blog> blogList = null;
        //1.查询
        try {
            blogList = blogExampleMapper.selectOnePublished(blogId);
        } catch (Exception e) {
            throw new ClientException(ClientErrorEmun.SELECT_ONE_ERROR,e.getMessage());
        }

        if (blogList.size() == 0){
            throw new ClientException(ClientErrorEmun.NO_BLOG_EXIST);
        }

        Blog blog = blogList.get(0);
        //2.拼接
        ClientDTO dto = (ClientDTO) BeanUtilsPlug.copyPropertiesReturnTarget(blog,new ClientDTO());

        //3.设置分类名
        BlogType type = null;
        try {
            type = blogTypeMapper.selectByPrimaryKey(dto.getClassId());
        } catch (Exception e) {
            throw new ClientException(ClientErrorEmun.NO_TYPE_EXIST,e.getMessage());
        }
        dto.setClassName(type.getClassName());

        //4.增加pv
        this.addPreview(blogId);

        return dto;
    }

    @Override
    public PreNextDTO preNext(Integer blogId) {

        PreNextDTO result = new PreNextDTO();

        //查询内容
        Blog blogPre = null;
        Blog blogNext = null;
        try {
            blogPre = blogMapper.pre(blogId);
            blogNext = blogMapper.next(blogId);
        } catch (Exception e) {
            throw new ClientException(ClientErrorEmun.NO_TYPE_EXIST);
        }

        if (null == blogPre){
            result.setPreId(-1);
            result.setPreTitle("no msg available");
        }else {
            result.setPreId(blogPre.getId());
            result.setPreTitle(blogPre.getTitle());
        }

        if (null == blogNext){
            result.setNextId(-1);
            result.setNextTitle("no msg available");
        }else {
            result.setNextId(blogNext.getId());
            result.setNextTitle(blogNext.getTitle());
        }

        return result;
    }

    @Override
    public ManageDTO listComment(Integer blogId, Integer offset, Integer limit) {

        //1.查询
        ManageDTO dto = null;
        try {
            dto = commentService.list(blogId,offset,limit);
        } catch (CommentException e) {
            throw new ClientException(ClientErrorEmun.LIST_COMMENT_ERROR,e.getMessage());
        }

        return dto;
    }

    @Override
    public Integer insertComment(CommentDTO dto) {

        Integer result = null;
        try {
            result = commentService.insert((BlogComment) BeanUtilsPlug
                    .copyPropertiesReturnTarget(dto,new BlogComment()));
        } catch (CommentException e) {
            throw new ClientException(ClientErrorEmun.INSERT_COMMENT_ERROR,e.getMessage());
        }

        //todo 发送邮件
        return result;
    }

    @Override
    @Transactional
    public synchronized Integer addPreview(Integer blogId) {

        //1.查询
        Blog  blog = blogMapper.selectByPrimaryKey(blogId);


        if (null == blog){
            throw new ClientException(ClientErrorEmun.NO_BLOG_EXIST);
        }

        //2.更新
        blog.setPv(blog.getPv()+1);

        Integer result = null;
        try {
            result = blogMapper.updateByPrimaryKey(blog);
        } catch (Exception e) {
            throw new ClientException(ClientErrorEmun.ADD_PV_ERROR);
        }

        return result;
    }
}
