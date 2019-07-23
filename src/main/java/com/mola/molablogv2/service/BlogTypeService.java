package com.mola.molablogv2.service;

import com.mola.molablogv2.dto.BlogTypeDTO;

/**
 * @Author: molamola
 * @Date: 19-6-27 下午2:44
 * @Version 1.0
 * 博客分类service
 */
public interface BlogTypeService {

    /**
     * 根据userId查找其创建的所有分类
     * @param userId
     * @return
     */
    BlogTypeDTO selectAllByUserId(Integer userId);

    /**
     * 根据id查找分类，返回递归子树
     * @param id
     * @return
     */
    BlogTypeDTO selectOneById(Integer id);

    /**
     * 更新分类
     * @param typeDTO
     * @return
     */
    Integer update(BlogTypeDTO typeDTO);

    /**
     * 插入分类
     * @param typeDTO
     * @return
     */
    Integer insert(BlogTypeDTO typeDTO);

    /**
     * 删除分类
     * @param typeDTO
     * @return
     */
    Integer delete(BlogTypeDTO typeDTO);
}
