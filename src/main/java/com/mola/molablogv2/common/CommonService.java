package com.mola.molablogv2.common;

import com.mola.molablogv2.annotation.SendEmail;
import com.mola.molablogv2.dto.CommentDTO;
import com.mola.molablogv2.pojo.BlogComment;
import com.mola.molablogv2.repository.BlogCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    /**
     * 发送邮件
     * @return
     */
    @SendEmail(responseAddress = "408287749@qq.com")
    public EmailParser send(CommentDTO comment, String blogTitle){

        String commentAlertEmailHtml = "<p>\n" +
                "    <em style=\"font-weight: bold;\">%s</em>于<em>%s</em>在你的博客<a href=\"%s\" target=\"_self\"><strong><em>%s</em></strong></a>中回复你：\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-size: 20px;\">%s</span>\n" +
                "</p>";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

        String msg = String.format(commentAlertEmailHtml,comment.getName(),dateFormat.format(new Date()),
                "http://47.107.243.217/molablog/page/"+comment.getBlogId(),blogTitle,comment.getContent());

        //初始化parser
        EmailParser parser = new EmailParser();
        parser.setSubject("来自"+comment.getName()+"在molablog中的回复");
        parser.setContent(msg);

        return parser;
    }
}
