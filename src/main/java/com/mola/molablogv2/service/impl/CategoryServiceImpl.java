package com.mola.molablogv2.service.impl;

import com.mola.molablogv2.dto.client.ClientDTO;
import com.mola.molablogv2.emun.ClientErrorEmun;
import com.mola.molablogv2.exception.ClientException;
import com.mola.molablogv2.pojo.Blog;
import com.mola.molablogv2.pojo.BlogType;
import com.mola.molablogv2.repository.BlogTypeMapper;
import com.mola.molablogv2.repository.example.BlogExampleMapper;
import com.mola.molablogv2.service.client.CategoryService;
import com.mola.molablogv2.utils.BeanUtilsPlug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: molamola
 * @Date: 19-7-13 下午4:28
 * @Version 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private BlogExampleMapper blogExampleMapper;

    @Autowired
    private BlogTypeMapper typeMapper;

    @Override
    public List<ClientDTO> list(Integer userId) {

        List<Blog> blogList = null;

        //查询
        try {
            blogList = blogExampleMapper.selectAllDescByUpdateTime(userId);
        } catch (Exception e) {
            throw new ClientException(ClientErrorEmun.SELECT_ALL_ERROR,e.getMessage());
        }

        //拼装
        //3.拼装
        List<ClientDTO> result = blogList.stream()
                .map(e -> (ClientDTO) BeanUtilsPlug.copyPropertiesReturnTarget(e,new ClientDTO()))
                .collect(Collectors.toList());

        //查找所有分类
        List<BlogType> typeList = typeMapper.selectAll();

        //建立type的map
        Map<Integer,String> typeMap = new HashMap();
        for (BlogType type : typeList){
            typeMap.put(type.getId(),type.getClassName());
        }

        //不需要的字段为null
        for (ClientDTO r : result){
            r.setCommentCount(null);
            r.setText(null);
            r.setUpdateTime(null);
            r.setClassName(typeMap.get(r.getClassId()));
        }

        return result;
    }
}
