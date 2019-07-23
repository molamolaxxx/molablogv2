package com.mola.molablogv2.service;

import com.mola.molablogv2.dto.ManageDTO;
import com.mola.molablogv2.dto.BlogDTO;

/**
 * @Author: molamola
 * @Date: 19-6-29 下午4:19
 * @Version 1.0
 */
public interface BlogService {

    /**
     * 分页查询博客
     * @param userId 用户名
     * @param offset　第几页
     * @param limit　每页多少条数据
     * @return
     */
    ManageDTO selectAll(Integer userId , Integer offset , Integer limit);

    /**
     * 查找博客的内容
     * @param blogId
     * @return
     */
    String selectContent(Integer blogId);

    /**
     * 插入博客
     * @param blogDTO
     * @return
     */
    Integer insert(BlogDTO blogDTO);

    /**
     * 更新博客
     * @param blogDTO
     * @return
     */
    Integer update(BlogDTO blogDTO);

    /**
     * 删除博客
     * @param blogDTO
     * @return
     */
    Integer delete(BlogDTO blogDTO);

    /**
     * 根据关键字查找博客
     * @param keyword
     * @param userId
     */
    ManageDTO search(String keyword , Integer userId);

}
