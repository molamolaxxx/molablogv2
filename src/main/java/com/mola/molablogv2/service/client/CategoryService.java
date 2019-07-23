package com.mola.molablogv2.service.client;

import com.mola.molablogv2.dto.client.ClientDTO;

import java.util.List;

/**
 * @Author: molamola
 * @Date: 19-7-13 下午4:27
 * @Version 1.0
 */
public interface CategoryService {

    /**
     * 列出所有分类目标
     * @param userId
     * @return
     */
    List<ClientDTO> list(Integer userId);

}
