package com.mola.molablogv2.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mola.molablogv2.dto.CommentDTO;
import com.mola.molablogv2.dto.ManageDTO;
import com.mola.molablogv2.emun.ClientErrorEmun;
import com.mola.molablogv2.emun.CommentErrorEmun;
import com.mola.molablogv2.exception.ClientException;
import com.mola.molablogv2.exception.CommentException;
import com.mola.molablogv2.pojo.Blog;
import com.mola.molablogv2.pojo.BlogComment;
import com.mola.molablogv2.repository.BlogCommentMapper;
import com.mola.molablogv2.repository.BlogMapper;
import com.mola.molablogv2.repository.example.CommentExampleMapper;
import com.mola.molablogv2.service.BlogCommentService;
import com.mola.molablogv2.utils.BeanUtilsPlug;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: molamola
 * @Date: 19-7-8 下午4:08
 * @Version 1.0
 */
@Service
@Slf4j
public class BlogCommentServiceImpl implements BlogCommentService {

    @Autowired
    private CommentExampleMapper commentExampleMapper;

    @Autowired
    private BlogCommentMapper commentMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public ManageDTO list(Integer blogId, Integer offset, Integer limit) {
        //1.开启分页器
        PageHelper.startPage(offset,limit,"blog_comment.time DESC");

        //2.根据blogId查找
        List<BlogComment> commentList = null;
        try {
            commentList = commentExampleMapper.selectAllByBlogId(blogId);
        } catch (Exception e) {
            throw new CommentException(CommentErrorEmun.COMMENT_SELECT_ALL_ERROR,e.getMessage());
        }

        PageInfo pageInfo = new PageInfo(commentList);

        //3.转化成dto
        List<CommentDTO> data = commentList.stream()
                .map(e -> (CommentDTO)BeanUtilsPlug.copyPropertiesReturnTarget(e,new CommentDTO()))
                .collect(Collectors.toList());

        ManageDTO<CommentDTO> result = new ManageDTO();
        result.setData(data);
        result.setTotalCount(new Long(pageInfo.getTotal()).intValue());

        return result;
    }

    @Override
    @Transactional
    public Integer delete(Integer id) {

        Integer result = null;

        try {
            result = commentMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            throw new CommentException(CommentErrorEmun.COMMENT_DELETE_ERROR,e.getMessage());
        }

        if (result != 1){
            throw new CommentException(CommentErrorEmun.COMMENT_DELETE_ERROR,
                    CommentErrorEmun.COMMENT_DELETE_ERROR.getMsg());
        }
        return result;
    }

    @Override
    @Transactional
    public Integer insert(BlogComment comment) {

        //1.查询是否存在博客
        Blog blog = blogMapper.selectByPrimaryKey(comment.getBlogId());
        if (null == blog){
            throw new ClientException(ClientErrorEmun.NO_BLOG_EXIST);
        }

        Integer result = null;
        try {
            result = commentMapper.insert(comment);
        } catch (Exception e) {
            throw new CommentException(CommentErrorEmun.COMMENT_INSERT_ERROR, e.getMessage());
        }

        return result;
    }

}
