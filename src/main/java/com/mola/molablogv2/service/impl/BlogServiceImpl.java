package com.mola.molablogv2.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mola.molablogv2.common.CommonService;
import com.mola.molablogv2.constant.SearchExampleConst;
import com.mola.molablogv2.dto.BlogDTO;
import com.mola.molablogv2.dto.ManageDTO;
import com.mola.molablogv2.emun.BlogErrorEmun;
import com.mola.molablogv2.emun.CommonErrorEmun;
import com.mola.molablogv2.exception.BlogException;
import com.mola.molablogv2.exception.CommonException;
import com.mola.molablogv2.pojo.Blog;
import com.mola.molablogv2.pojo.BlogComment;
import com.mola.molablogv2.pojo.BlogType;
import com.mola.molablogv2.repository.BlogCommentMapper;
import com.mola.molablogv2.repository.BlogMapper;
import com.mola.molablogv2.repository.BlogTypeMapper;
import com.mola.molablogv2.repository.example.BlogExampleMapper;
import com.mola.molablogv2.repository.example.SearchExampleMapper;
import com.mola.molablogv2.service.BlogService;
import com.mola.molablogv2.utils.CopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: molamola
 * @Date: 19-6-29 下午4:20
 * @Version 1.0
 */
@Service
@Slf4j
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogExampleMapper blogExampleMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogCommentMapper commentMapper;

    @Autowired
    private BlogTypeMapper typeMapper;

    @Autowired
    private SearchExampleMapper searchExampleMapper;

    @Autowired
    private CommonService commonService;


    @Override
    public ManageDTO selectAll(Integer userId, Integer offset, Integer limit) {

        //1.根据userId查找博客信息　pojo:blog
        //开启pagehelper
        PageHelper.startPage(offset,limit,"blog.create_time DESC");

        //查询
        List<Blog> blogList = blogExampleMapper.selectAllBlogByUserId(userId);

        //获得page信息
        PageInfo pageInfo = new PageInfo(blogList);

        //2.根据userId统计评论信息
        //查找所有评论
        List<BlogComment> commentList = commentMapper.selectAll();

        //建立blogId -> count 的索引
        Map<Integer,Integer> countMap = commonService.getTypeCountMap();

        //3.根据classId查询className
        List<BlogType> blogTypeList = typeMapper.selectAll();

        Map<Integer,String> typeMap = new HashMap<>();
        for (BlogType type : blogTypeList){
            typeMap.put(type.getId(),type.getClassName());
        }

        //4.拼装
        ManageDTO<BlogDTO> manageDTO = new ManageDTO();
        manageDTO.setTotalCount(new Long(pageInfo.getTotal()).intValue());
        List<BlogDTO> itemList = new ArrayList<>();

        for (Blog blog : blogList){
            BlogDTO item = new BlogDTO();
            item.setCommentCount(null == countMap.get(blog.getId())? 0 : countMap.get(blog.getId()));
            item.setClassName(typeMap.get(blog.getClassId()));
            BeanUtils.copyProperties(blog,item);
            itemList.add(item);
        }

        manageDTO.setData(itemList);

        return manageDTO;
    }

    @Override
    public String selectContent(Integer blogId) {
        //1.根据id查找博客
        Blog blog = blogMapper.selectByPrimaryKey(blogId);

        //2.空值判断
        if (null == blog){
            throw new BlogException(BlogErrorEmun.NO_BLOG_EXIST);
        }

        return blog.getContent();
    }

    @Override
    @Transactional
    public Integer insert(BlogDTO blogDTO) {

        Blog blog = new Blog();
        CopyUtils.copyProperties(blogDTO,blog);
        blog.setPv(0);

        Integer result = null;
        try {
            result = blogMapper.insert(blog);
        } catch (Exception e) {
            throw new BlogException(BlogErrorEmun.BLOG_INSERT_ERROR,e.getMessage());
        }

        return result;
    }

    @Override
    @Transactional
    public Integer update(BlogDTO blogDTO) {

        //1.根据blogDTOItem查询博客
        Blog blog = blogMapper.selectByPrimaryKey(blogDTO.getId());

        if (null == blog)
            throw new BlogException(BlogErrorEmun.NO_BLOG_EXIST);

        //2.将dto复制到blog
        CopyUtils.copyProperties(blogDTO,blog);

        //3.更新博客，将更新时间设为null
        blog.setUpdateTime(null);
        Integer result = null;

        try {
            result = blogMapper.updateByPrimaryKey(blog);
        } catch (Exception e) {
            throw new BlogException(BlogErrorEmun.BLOG_UPDATE_ERROR,e.getMessage());
        }

        if (result != 1){
            throw new BlogException(BlogErrorEmun.BLOG_UPDATE_ERROR);
        }

        return result;
    }

    @Override
    @Transactional
    public Integer delete(BlogDTO blogDTO) {

        Integer result = null;

        try {
            result = blogMapper.deleteByPrimaryKey(blogDTO.getId());
        } 
        catch (Exception e) {
            throw new BlogException(BlogErrorEmun.BLOG_DELETE_ERROR,e.getMessage());
        }

        if (result == 0){
            throw new BlogException(BlogErrorEmun.NO_BLOG_EXIST);
        }
        else if(result > 1){
            throw new BlogException(BlogErrorEmun.BLOG_DELETE_ERROR);
        }

        return result;
    }

    @Override
    public ManageDTO search(String keyword, Integer userId) {

        //题目搜索的结果
        List<Blog> titleResult=null;
        //摘要搜索的结果
        List<Blog> textResult=null;
        //分类搜索的结果
        List<Blog> typeResult=null;

        try {
            titleResult = searchExampleMapper.search(SearchExampleConst.BY_TITLE,keyword,userId);
            textResult = searchExampleMapper.search(SearchExampleConst.BY_CONTENT,keyword,userId);
            typeResult = searchExampleMapper.search(SearchExampleConst.BY_TYPE,keyword,userId);
        } catch (Exception e) {
            throw new BlogException(BlogErrorEmun.BLOG_SEARCH_ERROR,e.getMessage());
        }

        //hashmap保存每个搜索结果的得分比，出现在title给2分，出现在text给1分,全出现给3分
        Map<Integer,Integer> rateMap=new TreeMap<>();

        Map<Integer,Blog> pojoMap=new HashMap<>();
        if(titleResult!=null&&textResult!=null&&typeResult!=null) {
            for (Blog b : titleResult) {
                rateMap.put(b.getId(), 2);
                pojoMap.put(b.getId(),b);
            }

            for (Blog b:textResult){
                //如果已经存在rate
                if(rateMap.keySet().contains(b.getId())) {
                    rateMap.put(b.getId(), 3);
                }
                else {
                    rateMap.put(b.getId(), 1);
                    pojoMap.put(b.getId(),b);
                }
            }
            for (Blog b:typeResult){
                //如果已经存在rate
                if(rateMap.keySet().contains(b.getId())) {
                    rateMap.put(b.getId(), rateMap.get(b.getId())+1);
                }
                else {
                    rateMap.put(b.getId(), 1);
                    pojoMap.put(b.getId(),b);
                }
            }
        }
        List<Blog> searchResultList=new ArrayList<>();
        //将map.entrySet()转换成list
        List<Map.Entry<Integer,Integer>> list = new ArrayList<Map.Entry<Integer,Integer>>(rateMap.entrySet());
        //通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
                    @Override
                    public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                        return o2.getValue().compareTo(o1.getValue());
                    }
                }
        );
        //遍历map转化成的list
        for(Map.Entry<Integer,Integer> mapping:list){
            searchResultList.add(pojoMap.get(mapping.getKey()));
        }

        //转化成dto
        ManageDTO dto = new ManageDTO();
        dto.setData(new ArrayList<BlogDTO>());

        //typename
        List<BlogType> blogTypeList = typeMapper.selectAll();
        Map<Integer,String> typeMap = new HashMap<>();
        for (BlogType type : blogTypeList){
            typeMap.put(type.getId(),type.getClassName());
        }

        Map countMap = commonService.getTypeCountMap();
        for(Blog b : searchResultList){
            BlogDTO dtoItem = new BlogDTO();
            BeanUtils.copyProperties(b,dtoItem);
            try {
                dtoItem.setCommentCount(null == countMap.get(b.getId()) ? 0 : (Integer) countMap.get(b.getId()));
                dtoItem.setClassName(typeMap.get(b.getClassId()));
                dto.getData().add(dtoItem);

            } catch (Exception e) {
                throw new CommonException(CommonErrorEmun.STITCHING_ERROR,e.getMessage());
            }
        }

        dto.setTotalCount(dto.getData().size());

        return dto;
    }


}
