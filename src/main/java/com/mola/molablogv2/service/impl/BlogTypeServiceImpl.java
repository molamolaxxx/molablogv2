package com.mola.molablogv2.service.impl;

import com.mola.molablogv2.dto.BlogTypeDTO;
import com.mola.molablogv2.emun.TypeErrorEmun;
import com.mola.molablogv2.exception.TypeException;
import com.mola.molablogv2.pojo.BlogType;
import com.mola.molablogv2.repository.BlogTypeMapper;
import com.mola.molablogv2.repository.example.BlogExampleMapper;
import com.mola.molablogv2.repository.example.BlogTypeExampleMapper;
import com.mola.molablogv2.service.BlogTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: molamola
 * @Date: 19-6-27 下午2:45
 * @Version 1.0
 */
@Service
@Slf4j
public class BlogTypeServiceImpl implements BlogTypeService {

    @Autowired
    private BlogTypeExampleMapper blogTypeExampleMapper;

    @Autowired
    private BlogTypeMapper blogTypeMapper;

    @Autowired
    private BlogExampleMapper blogExampleMapper;

    @Override
    public BlogTypeDTO selectAllByUserId(Integer userId) {

        //1.通过userId查询所有的博客分类
        List<BlogType> blogTypeList = blogTypeExampleMapper.selectAllTypesByUserId(userId);

        //2.将博客分类拼装成DTO格式 (1)建立map (2)递归添加子节点
        Map<Integer,List<BlogType>> map =new HashMap<>();
        for (BlogType type :blogTypeList){
            if (null == map.get(type.getParentId())){
                map.put(type.getParentId(),new ArrayList<>());
                map.get(type.getParentId()).add(type);
            }
            else {
                map.get(type.getParentId()).add(type);
            }
        }
        BlogType root = new BlogType();
        root.setId(0);
        root.setClassName("root");

        BlogTypeDTO blogTypeDTO = ToBlogTypeDTO(map,root);

        return blogTypeDTO;
    }

    /**
     * 数据库信息转化为dto
     * @param
     * @return
     */
    private BlogTypeDTO ToBlogTypeDTO(Map<Integer,List<BlogType>> map,BlogType blogType){

        //建立根节点
        BlogTypeDTO blogTypeDTO = new BlogTypeDTO();
        blogTypeDTO.setId(blogType.getId());
        blogTypeDTO.setClassName(blogType.getClassName());
        blogTypeDTO.setChildren(new ArrayList<>());

        //如果不是根节点
        if (null == map.get(blogTypeDTO.getId())){
            return blogTypeDTO;
        }
        //添加子节点
        for (BlogType type : map.get(blogTypeDTO.getId())) {
            blogTypeDTO.getChildren().add(ToBlogTypeDTO(map,type));
        }
        return blogTypeDTO;
    }

    @Override
    public BlogTypeDTO selectOneById(Integer id){
        
        //查找判断分类是否存在
        BlogType type = blogTypeMapper.selectByPrimaryKey(id);

        if (null == type)
            throw new TypeException(TypeErrorEmun.NO_SUCH_TYPE_EXIST);

        return getRootNode(type);
    }

    public BlogTypeDTO getRootNode(BlogType type){

        //2.判断是否为父节点，子节点递归寻找父节点
        BlogTypeDTO dto = new BlogTypeDTO();
        dto.setId(type.getId());
        dto.setClassName(type.getClassName());

        if (type.getParentId() != 0) {

            BlogTypeDTO father = getRootNode(blogTypeMapper.selectByPrimaryKey(type.getParentId()));
            BlogTypeDTO lastNode = null;

            lastNode = father.getChildren() == null ? father:father.getChildren().get(0);

            while (lastNode.getChildren() != null){
                lastNode = lastNode.getChildren().get(0);
            }

            lastNode.setChildren(Arrays.asList(dto));
            return father;
        }

        return dto;
    }

    @Override
    @Transactional
    public Integer update(BlogTypeDTO typeDTO) {
        //1.查找是否存在type
        BlogType blogType = blogTypeMapper.selectByPrimaryKey(typeDTO.getId());

        if (null == blogType) {
            log.error("分类不存在");
            throw new TypeException(TypeErrorEmun.NO_SUCH_TYPE_EXIST);
        }

        //2.更新
        blogType.setClassName(typeDTO.getClassName());
        
        Integer result = null;
        try {
            result = blogTypeMapper.updateByPrimaryKey(blogType);
        } catch (Exception e) {
            throw new TypeException(TypeErrorEmun.TYPE_UPDATE_ERROR,e.getMessage());
        }

        //如果影响行数不为1
        if (result != 1) {
            log.error("更新分类出错");
            throw new TypeException(TypeErrorEmun.TYPE_UPDATE_ERROR);
        }

        return result;
    }

    @Override
    @Transactional
    public Integer insert(BlogTypeDTO typeDTO) {

        BlogType type = new BlogType();

        BeanUtils.copyProperties(typeDTO,type);

        Integer result = null;
        
        try {
            result = blogTypeMapper.insert(type);
        } catch (Exception e) {
            throw new TypeException(TypeErrorEmun.TYPE_INSERT_ERROR,e.getMessage());
        }

        //如果影响行数不为1
        if (result != 1) {
            log.error("插入分类出错");
            throw new TypeException(TypeErrorEmun.TYPE_INSERT_ERROR);
        }

        return result;
    }

    @Override
    @Transactional
    public Integer delete(BlogTypeDTO typeDTO) {

        //1.按照userId获取所有分类数据
        List<BlogType> typeList = blogTypeExampleMapper.selectAllTypesByUserId(typeDTO.getUserId());

        //以parentId为索引建立map以查找子分类
        HashMap<Integer,List<BlogType>> map = new HashMap<>();
        for (BlogType type:typeList) {
            if (map.get(type.getParentId()) == null){
                map.put(type.getParentId(),new ArrayList<>());
            }
            map.get(type.getParentId()).add(type);
        }

        Integer result = deleteChildren(typeDTO.getId(),map);

        if (result == -1){
            throw new TypeException(TypeErrorEmun.TYPE_DELETE_ERROR);
        }
        return result;
    }

    public Integer deleteChildren(Integer id,HashMap<Integer,List<BlogType>> map){
        //递归处理子分类
        if (map.get(id)!=null){
            for (BlogType type:map.get(id)) {
                int result=deleteChildren(type.getId(),map);
                if(result==-1){
                    return -1;
                }
            }
        }
        //删除分类以及所属文章
        try{
            blogTypeMapper.deleteByPrimaryKey(id);
            //删除对应博客
            blogExampleMapper.deleteBlogByClassId(id);

            return 1;
        }catch (Exception e){
            
            return -1;
        }
    }
}
