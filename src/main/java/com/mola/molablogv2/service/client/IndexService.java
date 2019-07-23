package com.mola.molablogv2.service.client;

import com.mola.molablogv2.dto.client.ClientDTO;

import java.util.List;


/**
 * @Author: molamola
 * @Date: 19-7-11 上午10:54
 * @Version 1.0
 */
public interface IndexService {

    /**
     *
     * @param userId
     * @param offset
     * @param limit
     * @param descType
     * @return
     */
    List<ClientDTO> list(Integer userId, Integer offset, Integer limit, Integer descType);
}
