package com.mola.molablogv2.dto.client;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mola.molablogv2.utils.serializer.Date2LongSerializer;

import lombok.Data;
import java.util.Date;

/**
 * @Author: molamola
 * @Date: 19-7-11 上午10:45
 * @Version 1.0
 * client端dto
 */
@Data
public class ClientDTO {

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
     * 内容，纯文本
     */
    private String text;

    /**
     * 内容，html
     */
    private String content;

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

    /**
     * 分类id
     */
    private Integer classId;

    /**
     * 分类名称
     */
    private String className;
}
