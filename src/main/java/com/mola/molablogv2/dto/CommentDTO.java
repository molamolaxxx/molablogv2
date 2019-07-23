package com.mola.molablogv2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mola.molablogv2.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @Author: molamola
 * @Date: 19-7-8 下午4:12
 * @Version 1.0
 */
@Data
public class CommentDTO {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blog_comment.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blog_comment.time
     *
     * @mbg.generated
     */
    @JsonProperty("createTime")
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date time;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blog_comment.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blog_comment.friend_link
     *
     * @mbg.generated
     */
    private String friendLink;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blog_comment.ip
     *
     * @mbg.generated
     */
    private String ip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blog_comment.blog_id
     *
     * @mbg.generated
     */
    private Integer blogId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blog_comment.email
     *
     * @mbg.generated
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blog_comment.img_link
     *
     * @mbg.generated
     */
    private String imgLink;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blog_comment.content
     *
     * @mbg.generated
     */
    private String content;
}
