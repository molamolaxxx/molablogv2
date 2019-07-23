package com.mola.molablogv2.service.client;

import com.mola.molablogv2.dto.client.ClientDTO;

import java.util.List;

/**
 * @Author: molamola
 * @Date: 19-7-22 上午9:40
 * @Version 1.0
 * 客户端查找service
 */
public interface SearchService {

    /**
     * 客户端查找博客
     * @param keyword
     * @param userId
     * @return
     */
    List<ClientDTO> search(String keyword, Integer userId);
}
