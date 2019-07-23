package com.mola.molablogv2.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: molamola
 * @Date: 19-7-22 下午5:25
 * @Version 1.0
 * 评论提交表单
 */
@Data
public class CommentForm {

    /**
     * 评论的博客id
     */
    private Integer blogId;

    /**
     * 博客题目
     */
    private String blogName;

    /**
     * 评论内容
     */
    @NotEmpty(message = "评论内容不为空")
    private String content;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 友情链接
     */
    private String friendLink;

    /**
     * 头像图片链接
     */
    private String imgLink;

    /**
     * 昵称
     */
    @NotEmpty(message = "昵称不为空")
    private String name;

    /**
     * token
     */
    private String token;
}
