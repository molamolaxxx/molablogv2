package com.mola.molablogv2.service;

import com.mola.molablogv2.dto.ManageDTO;
import com.mola.molablogv2.pojo.BlogComment;

/**
 * @Author: molamola
 * @Date: 19-7-8 下午4:08
 * @Version 1.0
 */
public interface BlogCommentService {

    /**
     * 查找所有博客评论
     * @param blogId
     * @param offset
     * @param limit
     * @return
     */
    ManageDTO list(Integer blogId, Integer offset, Integer limit);

    /**
     * 根据id删除评论
     * @param id
     * @return
     */
    Integer delete(Integer id);

    /**
     * 插入评论
     * @param comment
     * @return
     */
    Integer insert(BlogComment comment);

}
