package com.mola.molablogv2.service.impl;

import com.github.pagehelper.PageHelper;
import com.mola.molablogv2.common.CommonService;
import com.mola.molablogv2.constant.ClientDescConst;
import com.mola.molablogv2.dto.client.ClientDTO;
import com.mola.molablogv2.emun.ClientErrorEmun;
import com.mola.molablogv2.exception.ClientException;
import com.mola.molablogv2.pojo.Blog;
import com.mola.molablogv2.repository.example.BlogExampleMapper;
import com.mola.molablogv2.service.client.IndexService;
import com.mola.molablogv2.utils.BeanUtilsPlug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: molamola
 * @Date: 19-7-11 上午10:58
 * @Version 1.0
 */
@Service
public class IndexServiceImpl implements IndexService{

    @Autowired
    private BlogExampleMapper blogExampleMapper;

    @Autowired
    private CommonService commonService;

    @Override
    public List<ClientDTO> list(Integer userId, Integer offset, Integer limit, Integer descType) {

        //1.pageHelper分页
        switch (descType){
            case ClientDescConst.BY_CREATE_TIME:{
                PageHelper.startPage(offset,limit,"blog.create_time DESC");
                break;
            }
            case ClientDescConst.BY_PV:{
                PageHelper.startPage(offset,limit,"blog.pv DESC");
                break;
            }
        }

        //2.查询
        List<Blog> blogList = null;
        try {
            blogList = blogExampleMapper.selectAllPublishedBlogByUserId(userId);
        } catch (Exception e) {
            throw new ClientException(ClientErrorEmun.SELECT_ALL_ERROR,e.getMessage());
        }

        //3.拼装
        List<ClientDTO> dtoList = blogList.stream()
                .map(e -> (ClientDTO)BeanUtilsPlug.copyPropertiesReturnTarget(e,new ClientDTO()))
                .collect(Collectors.toList());

        Map typeCountMap = commonService.getTypeCountMap();

        for(ClientDTO dto : dtoList){
            dto.setCommentCount(null == typeCountMap.get(dto.getId()) ? 0 : (Integer) typeCountMap.get(dto.getId()));
        }

        return dtoList;
    }

}
