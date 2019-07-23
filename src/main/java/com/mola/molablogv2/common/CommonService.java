package com.mola.molablogv2.common;

import com.mola.molablogv2.pojo.BlogComment;
import com.mola.molablogv2.repository.BlogCommentMapper;
import com.mola.molablogv2.repository.example.CommentExampleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: molamola
 * @Date: 19-7-11 上午11:42
 * @Version 1.0
 * 公共service
 */
@Service
public class CommonService {

    @Autowired
    private BlogCommentMapper commentMapper;

    /**
     * map(blogId -> commentCount)
     * @return
     */
    public Map getTypeCountMap(){
        //查找所有评论
        List<BlogComment> commentList = commentMapper.selectAll();

        //建立blogId -> count 的索引
        Map<Integer,Integer> countMap = new HashMap<>();
        for (BlogComment comment :commentList){
            Integer count = countMap.get(comment.getBlogId());
            if (null == count){
                countMap.put(comment.getBlogId(),1);
                continue;
            }
            countMap.put(comment.getBlogId(),count+1);
        }

        return countMap;
    }
}
