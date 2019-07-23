package com.mola.molablogv2.service.client;

import com.mola.molablogv2.dto.CommentDTO;
import com.mola.molablogv2.dto.ManageDTO;
import com.mola.molablogv2.dto.client.ClientDTO;
import com.mola.molablogv2.dto.client.PreNextDTO;

/**
 * @Author: molamola
 * @Date: 19-7-15 上午11:53
 * @Version 1.0
 */
public interface PageService {

    /**
     * 根据博客id查询博客
     * @param blogId
     * @return
     */
    ClientDTO selectOne(Integer blogId);

    /**
     * 根据博客id返回前后博客题目
     * @param blogId
     * @return
     */
    PreNextDTO preNext(Integer blogId);

    /**
     * 增加pv
      * @param blogId
     * @return
     */
     Integer addPreview(Integer blogId);

    /**
     * 插入评论
     * @return
     */
     Integer insertComment(CommentDTO dto);

    /**
     * 客户端获取评论列表
     * @return
     */
    ManageDTO listComment(Integer blogId, Integer offset, Integer limit);
}
