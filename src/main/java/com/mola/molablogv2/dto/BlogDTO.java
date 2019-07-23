package com.mola.molablogv2.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mola.molablogv2.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @Author: molamola
 * @Date: 19-7-1 下午5:11
 * @Version 1.0
 * 博客数据数据
 */
@Data
public class BlogDTO {

    /**
     * 博客id
     */
    private Integer id;

    /**
     * 博客题目
     */
    private String title;

    /**
     * 博客浏览量
     */
    private Integer pv;

    /**
     * 是否发表　0发表　1未发表
     */
    private Integer published;

    /**
     *  内容，包括html
     */
    private String content;

    /**
     * 内容，纯文本
     */
    private String text;

    /**
     * 博客用户id
     */
    private Integer userId;

    /**
     * 博客分类id
     */
    private Integer classId;

    /**
     * 博客分类名称
     */
    private String className;

    /**
     * 博客创建时间
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /**
     * 博客更新时间
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /**
     * 评论个数
     */
    private Integer commentCount;
}
