package com.mola.molablogv2.service.impl;

import com.mola.molablogv2.dto.BlogDTO;
import com.mola.molablogv2.dto.ManageDTO;
import com.mola.molablogv2.dto.client.ClientDTO;
import com.mola.molablogv2.service.BlogService;
import com.mola.molablogv2.service.client.SearchService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: molamola
 * @Date: 19-7-22 上午9:40
 * @Version 1.0
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private BlogService blogService;

    @Override
    public List<ClientDTO> search(String keyword, Integer userId) {

        //1.调用blogService
        ManageDTO dto = blogService.search(keyword,userId);
        List<BlogDTO> blogDTOList = dto.getData();

        //2.转化
        List<ClientDTO> result = new ArrayList<>();
        for (BlogDTO d : blogDTOList){
            ClientDTO one = new ClientDTO();
            BeanUtils.copyProperties(d,one);
            //去掉无用项
            one.setContent(null);
            one.setClassName(null);
            one.setClassId(null);

            result.add(one);
        }

        return result;
    }
}
